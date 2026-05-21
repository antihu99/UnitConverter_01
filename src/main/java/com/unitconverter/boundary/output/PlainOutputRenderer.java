package com.unitconverter.boundary.output;

import com.unitconverter.entity.ConversionResult;
import java.util.List;

public class PlainOutputRenderer {

    public String render(String sourceUnit, double sourceAmount, List<ConversionResult> results) {
        StringBuilder sb = new StringBuilder();
        for (ConversionResult result : results) {
            sb.append(String.format(
                    java.util.Locale.US,
                    "%.1f %s = %.1f %s%n",
                    sourceAmount,
                    sourceUnit,
                    roundOne(result.targetAmount()),
                    result.targetUnit()));
        }
        return sb.toString().trim();
    }

    private static double roundOne(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
