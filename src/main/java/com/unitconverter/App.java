package com.unitconverter;

import com.unitconverter.boundary.output.PlainOutputRenderer;
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
        PlainOutputRenderer renderer = new PlainOutputRenderer();

        if (args.length == 0) {
            System.err.println("Usage: java -jar unit-converter.jar meter:2.5");
            return;
        }

        ParsedInput input = parser.parseConversionLine(args[0]);
        validator.validate(input);
        List<ConversionResult> results = engine.convertAll(input.unit(), input.amount());
        System.out.println(renderer.render(input.unit(), input.amount(), results));
    }
}
