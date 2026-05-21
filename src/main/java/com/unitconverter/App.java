package com.unitconverter;

import com.unitconverter.boundary.parser.InputValidator;
import com.unitconverter.boundary.parser.LineParser;
import com.unitconverter.boundary.parser.ParsedInput;
import com.unitconverter.entity.ConversionEngine;
import com.unitconverter.entity.ConversionResult;
import com.unitconverter.entity.UnitRegistry;
import java.util.List;

public class App {

    public static void main(String[] args) {
        UnitRegistry registry = UnitRegistry.withDefaults();
        ConversionEngine engine = new ConversionEngine(registry);
        LineParser parser = new LineParser();
        InputValidator validator = new InputValidator(registry);

        if (args.length == 0) {
            System.err.println("Usage: java -jar unit-converter.jar meter:2.5");
            return;
        }

        ParsedInput input = parser.parseConversionLine(args[0]);
        validator.validate(input);
        List<ConversionResult> results = engine.convertAll(input.unit(), input.amount());
        for (ConversionResult result : results) {
            System.out.printf(
                    "%s %s = %.1f %s%n",
                    formatAmount(input.amount()),
                    input.unit(),
                    roundOne(result.targetAmount()),
                    result.targetUnit());
        }
    }

    private static String formatAmount(double amount) {
        if (amount == (long) amount) {
            return Long.toString((long) amount);
        }
        return Double.toString(amount);
    }

    private static double roundOne(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
