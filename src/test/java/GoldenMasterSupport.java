import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Golden Master capture ({@link System#setOut}) and section-wise approval against
 * {@code src/test/resources/golden_master_expected.txt}.
 */
final class GoldenMasterSupport {

    static final String GOLDEN_RESOURCE = "golden_master_expected.txt";
    static final Path GOLDEN_PATH = Paths.get("src", "test", "resources", GOLDEN_RESOURCE);

    static final List<String> SCENARIOS =
            List.of("meter:2.5", "feet:1.0", "yard:1.0", "meter:0.0");

    private GoldenMasterSupport() {
    }

    static String captureSectionWithSystemOut(UnitConverter converter, String scenario) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        PrintStream original = System.out;
        try {
            System.setOut(capture);
            System.out.println("[" + scenario + "]");
            System.out.println(converter.renderPlain(scenario).replace("\r\n", "\n"));
        } finally {
            System.setOut(original);
            capture.flush();
        }
        return normalizeSection(buffer.toString(StandardCharsets.UTF_8));
    }

    static Map<String, String> loadSectionsFromGoldenFile() throws IOException {
        if (!Files.exists(GOLDEN_PATH)) {
            throw new IOException("Missing baseline: " + GOLDEN_PATH.toAbsolutePath()
                    + ". Run scripts/generate-golden-master.ps1 then git add the file.");
        }
        String document = normalize(Files.readString(GOLDEN_PATH, StandardCharsets.UTF_8));
        Map<String, String> sections = new LinkedHashMap<>();
        for (String block : document.split("\n---\n")) {
            String trimmed = block.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            int newline = trimmed.indexOf('\n');
            if (newline < 0 || !trimmed.startsWith("[")) {
                throw new IOException("Invalid golden section header in " + GOLDEN_PATH);
            }
            String header = trimmed.substring(0, newline).trim();
            String key = header.substring(1, header.length() - 1);
            sections.put(key, normalizeSection(trimmed));
        }
        return sections;
    }

    static String buildFullGoldenDocument(UnitConverter converter) {
        StringBuilder document = new StringBuilder();
        for (int i = 0; i < SCENARIOS.size(); i++) {
            document.append(captureSectionWithSystemOut(converter, SCENARIOS.get(i)).stripTrailing());
            if (i < SCENARIOS.size() - 1) {
                document.append("\n---\n");
            } else {
                document.append('\n');
            }
        }
        return document.toString();
    }

    static void writeGoldenFile(String content) {
        try {
            Files.createDirectories(GOLDEN_PATH.getParent());
            Files.writeString(GOLDEN_PATH, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write " + GOLDEN_PATH, e);
        }
    }

    static String unifiedDiff(String expected, String actual) {
        List<String> expectedLines = Arrays.asList(expected.split("\n", -1));
        List<String> actualLines = Arrays.asList(actual.split("\n", -1));
        StringBuilder out = new StringBuilder();
        out.append("--- expected").append('\n');
        out.append("+++ actual").append('\n');

        int max = Math.max(expectedLines.size(), actualLines.size());
        for (int i = 0; i < max; i++) {
            String exp = i < expectedLines.size() ? expectedLines.get(i) : null;
            String act = i < actualLines.size() ? actualLines.get(i) : null;
            if (safeLine(exp).equals(safeLine(act))) {
                continue;
            }
            out.append(String.format(Locale.US, "@@ -%d +%d @@%n", i + 1, i + 1));
            if (exp != null) {
                out.append('-').append(exp).append('\n');
            }
            if (act != null) {
                out.append('+').append(act).append('\n');
            }
        }
        return out.toString();
    }

    private static String safeLine(String line) {
        return line == null ? "" : line;
    }

    static String formatMismatchMessage(String scenario, String expected, String actual) {
        return "Golden Master mismatch [" + scenario + "] in " + GOLDEN_PATH + System.lineSeparator()
                + unifiedDiff(expected, actual);
    }

    static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }

    static String normalizeSection(String text) {
        return normalize(text).stripTrailing() + "\n";
    }

    static void runGeneratorMain(String[] args) throws IOException {
        UnitConverter converter = new UnitConverter();
        String content = buildFullGoldenDocument(converter);
        boolean write = Arrays.stream(args).anyMatch(a -> "--write".equals(a) || "-w".equals(a));
        if (write) {
            writeGoldenFile(content);
            System.out.println("Wrote " + GOLDEN_PATH.toAbsolutePath());
        } else {
            System.out.print(content);
        }
    }
}
