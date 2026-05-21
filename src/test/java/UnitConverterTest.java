import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.unitconverter.entity.ConversionConstants;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitConverterTest {

    @Test
    @DisplayName("TC-B-01: convert meter to feet (delta 1e-5)")
    void tcB01_convertMeterToFeet() {
        UnitConverter converter = new UnitConverter();
        assertEquals(8.20210, converter.convert("meter", 2.5, "feet"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-02: convert meter to yard (delta 1e-5)")
    void tcB02_convertMeterToYard() {
        UnitConverter converter = new UnitConverter();
        assertEquals(1.09361, converter.convert("meter", 1.0, "yard"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-03: convert feet to meter reverse (delta 1e-5)")
    void tcB03_convertFeetToMeter() {
        UnitConverter converter = new UnitConverter();
        assertEquals(0.30480, converter.convert("feet", 1.0, "meter"), 1e-5);
    }

    @Test
    @DisplayName("TC-A-02: missing colon throws IllegalArgumentException")
    void tcA02_missingColon_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("meter"));
    }

    @Test
    @DisplayName("TC-A-03: negative value throws IllegalArgumentException")
    void tcA03_negativeValue_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("meter:-1.0"));
    }

    @Test
    @DisplayName("TC-A-01: happy path meter:2.5 returns conversions")
    void tcA01_parseAndConvert_happyPath() {
        UnitConverter converter = new UnitConverter();
        List<UnitConverter.ConversionRow> rows = converter.parseAndConvert("meter:2.5");
        assertEquals(3, rows.size());
        double feet = rows.stream()
                .filter(r -> "feet".equals(r.unit))
                .findFirst()
                .orElseThrow()
                .value;
        assertEquals(8.20210, feet, 1e-5);
    }

    @Test
    @DisplayName("TC-A-06: renderPlain preserves source unit and value")
    void tcA06_renderPlain_preservesSource() {
        UnitConverter converter = new UnitConverter();
        String output = converter.renderPlain("meter:2.5");
        assertTrue(output.contains("2.5 meter"));
        assertTrue(output.contains("8.2 feet"));
    }

    @Test
    @DisplayName("TC-A-07: value zero is valid")
    void tcA07_zeroValue_isValid() {
        UnitConverter converter = new UnitConverter();
        converter.parse("meter:0");
        assertEquals(0.0, converter.convert("meter", 0.0, "feet"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-04: convertAll returns all registered units")
    void tcB04_convertAll_returnsAllRegisteredUnits() {
        UnitConverter converter = new UnitConverter();
        List<UnitConverter.ConversionRow> rows = converter.convertAll("meter", 1.0);
        assertEquals(3, rows.size());
    }

    @Test
    @DisplayName("TC-B-05: registerUnit cubit then convert to meter")
    void tcB05_registerUnit_thenConvert() {
        UnitConverter converter = new UnitConverter();
        converter.registerUnit("cubit", 0.4572);
        assertEquals(0.4572, converter.convert("cubit", 1.0, "meter"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-06: loadConfig valid file applies ratios")
    void tcB06_loadConfig_validFile() {
        UnitConverter converter = new UnitConverter();
        converter.loadConfig("src/test/resources/units-valid.json");
        assertEquals(3.28084, converter.convert("meter", 1.0, "feet"), 1e-4);
    }

    @Test
    @DisplayName("TC-B-07: loadConfig missing file keeps defaults")
    void tcB07_loadConfig_missingFile_keepsDefaults() {
        UnitConverter converter = new UnitConverter();
        converter.loadConfig("src/test/resources/units-missing.json");
        assertEquals(
                1.0 * ConversionConstants.METER_TO_FEET,
                converter.convert("meter", 1.0, "feet"),
                1e-5);
    }

    @Test
    @DisplayName("TC-A-05: non-numeric amount throws IllegalArgumentException")
    void tcA05_nonNumericAmount_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("meter:abc"));
    }

    @Test
    @DisplayName("TC-A-04: unknown unit throws IllegalArgumentException")
    void tcA04_unknownUnit_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("parsec:1.0"));
    }
}
