import com.unitconverter.entity.ConversionConstants;

public class UnitConverter {

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        if ("meter".equals(sourceUnit) && "feet".equals(targetUnit)) {
            return sourceAmount * ConversionConstants.METER_TO_FEET;
        }
        if ("meter".equals(sourceUnit) && "yard".equals(targetUnit)) {
            return sourceAmount * ConversionConstants.METER_TO_YARD;
        }
        return 0.0;
    }

    public void parse(String line) {
        if (line == null || !line.contains(":")) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
        String[] parts = line.split(":", 2);
        double amount = Double.parseDouble(parts[1].trim());
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-VAL-002]: Length must be non-negative. Got " + amount + ".");
        }
    }
}
