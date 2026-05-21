import com.unitconverter.entity.ConversionConstants;

public class UnitConverter {

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        if ("meter".equals(sourceUnit) && "feet".equals(targetUnit)) {
            return sourceAmount * ConversionConstants.METER_TO_FEET;
        }
        return 0.0;
    }

    public void parse(String line) {
        if (line == null || !line.contains(":")) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
    }
}
