import com.unitconverter.entity.ConversionConstants;

public class UnitConverter {

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        if ("meter".equals(sourceUnit) && "feet".equals(targetUnit)) {
            return sourceAmount * ConversionConstants.METER_TO_FEET;
        }
        return 0.0;
    }
}
