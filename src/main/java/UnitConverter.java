import com.unitconverter.entity.ConversionConstants;

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
        double amount = Double.parseDouble(parts[1].trim());
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
