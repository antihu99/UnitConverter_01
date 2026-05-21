import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitconverter.entity.ConversionConstants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnitConverter {

    private final Map<String, Double> metersPerOneUnit = new LinkedHashMap<>();

    public UnitConverter() {
        resetToDefaults();
    }

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        requireKnownUnit(sourceUnit);
        requireKnownUnit(targetUnit);
        double amountInMeters = sourceAmount * metersPerOneUnit.get(sourceUnit);
        return amountInMeters / metersPerOneUnit.get(targetUnit);
    }

    public void parse(String line) {
        if (line == null || !line.contains(":")) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
        String[] parts = line.split(":", 2);
        String unit = parts[0].trim();
        double amount;
        try {
            amount = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-VAL-002]: Length must be non-negative. Got " + amount + ".");
        }
        requireKnownUnit(unit);
    }

    private void requireKnownUnit(String unit) {
        if (!metersPerOneUnit.containsKey(unit)) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-DOM-003]: Unknown unit \"" + unit + "\".");
        }
    }

    public void registerUnit(String unitName, double metersPerOne) {
        metersPerOneUnit.put(unitName, metersPerOne);
    }

    public List<ConversionRow> parseAndConvert(String line) {
        parse(line);
        String[] parts = line.split(":", 2);
        return convertAll(parts[0].trim(), Double.parseDouble(parts[1].trim()));
    }

    public String renderPlain(String line) {
        String[] parts = line.split(":", 2);
        String unit = parts[0].trim();
        double value = Double.parseDouble(parts[1].trim());
        List<ConversionRow> rows = parseAndConvert(line);
        StringBuilder sb = new StringBuilder();
        for (ConversionRow row : rows) {
            sb.append(String.format(
                    java.util.Locale.US,
                    "%.1f %s = %.1f %s%n",
                    value,
                    unit,
                    roundOneDecimal(row.value),
                    row.unit));
        }
        return sb.toString().trim();
    }

    private static double roundOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public List<ConversionRow> convertAll(String sourceUnit, double sourceAmount) {
        requireKnownUnit(sourceUnit);
        double amountInMeters = sourceAmount * metersPerOneUnit.get(sourceUnit);
        List<ConversionRow> results = new ArrayList<>();
        for (Map.Entry<String, Double> entry : metersPerOneUnit.entrySet()) {
            double converted = amountInMeters / entry.getValue();
            results.add(new ConversionRow(entry.getKey(), converted));
        }
        return results;
    }

    public void loadConfig(String path) {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            resetToDefaults();
            return;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(filePath.toFile());
            metersPerOneUnit.clear();
            metersPerOneUnit.put("meter", ConversionConstants.METERS_PER_METER);
            JsonNode units = root.get("units");
            if (units != null && units.isArray()) {
                for (JsonNode unit : units) {
                    metersPerOneUnit.put(
                            unit.get("name").asText(),
                            unit.get("metersPerOneUnit").asDouble());
                }
            }
        } catch (Exception e) {
            resetToDefaults();
        }
    }

    private void resetToDefaults() {
        metersPerOneUnit.clear();
        metersPerOneUnit.put("meter", ConversionConstants.METERS_PER_METER);
        metersPerOneUnit.put("feet", ConversionConstants.METERS_PER_FEET);
        metersPerOneUnit.put("yard", ConversionConstants.METERS_PER_YARD);
    }

    public static final class ConversionRow {
        public final String unit;
        public final double value;

        public ConversionRow(String unit, double value) {
            this.unit = unit;
            this.value = value;
        }
    }
}
