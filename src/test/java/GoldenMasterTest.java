import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Golden Master regression: {@link System#setOut} capture vs {@code golden_master_expected.txt}.
 *
 * <p>Run only golden tests: {@code mvn test -Dgroups=golden_master}
 */
@Tag("golden_master")
class GoldenMasterTest {

    private static Map<String, String> goldenSections;
    private static UnitConverter converter;

    @BeforeAll
    static void loadBaseline() throws IOException {
        converter = new UnitConverter();
        goldenSections = GoldenMasterSupport.loadSectionsFromGoldenFile();
    }

    @Test
    @DisplayName("GM-TC-01: meter:2.5 output == golden [meter:2.5] section")
    void unitConverter_meter_2_5() throws IOException {
        assertSectionMatchesGolden("meter:2.5");
    }

    @Test
    @DisplayName("GM-TC-02: feet:1.0 output == golden [feet:1.0] section")
    void unitConverter_feet_1_0() throws IOException {
        assertSectionMatchesGolden("feet:1.0");
    }

    @Test
    @DisplayName("GM-TC-03: yard:1.0 output == golden [yard:1.0] section")
    void unitConverter_yard_1_0() throws IOException {
        assertSectionMatchesGolden("yard:1.0");
    }

    @Test
    @DisplayName("GM-TC-04: meter:0.0 output == golden [meter:0.0] section")
    void unitConverter_meter_0_0() throws IOException {
        assertSectionMatchesGolden("meter:0.0");
    }

    private static void assertSectionMatchesGolden(String scenario) throws IOException {
        String expected = resolveExpected(scenario);
        String actual = GoldenMasterSupport.captureSectionWithSystemOut(converter, scenario);

        try {
            assertEquals(expected, actual, () -> GoldenMasterSupport.formatMismatchMessage(scenario, expected, actual));
        } catch (AssertionError e) {
            System.err.println(GoldenMasterSupport.formatMismatchMessage(scenario, expected, actual));
            throw e;
        }
    }

    private static String resolveExpected(String scenario) throws IOException {
        String expected = goldenSections.get(scenario);
        if (expected != null) {
            return expected;
        }
        if (!Files.exists(GoldenMasterSupport.GOLDEN_PATH)) {
            String actual = GoldenMasterSupport.captureSectionWithSystemOut(converter, scenario);
            GoldenMasterSupport.writeGoldenFile(GoldenMasterSupport.buildFullGoldenDocument(converter));
            goldenSections = GoldenMasterSupport.loadSectionsFromGoldenFile();
            expected = goldenSections.get(scenario);
            if (expected == null) {
                fail("Failed to create golden section for " + scenario);
            }
            return expected;
        }
        fail("Missing section [" + scenario + "] in " + GoldenMasterSupport.GOLDEN_PATH
                + ". Run scripts/generate-golden-master.ps1");
        return "";
    }
}
