package com.unitconverter;

import com.unitconverter.control.ConvertLengthUseCase;
import com.unitconverter.entity.UnitRegistry;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java -jar unit-converter.jar meter:2.5");
            return;
        }

        UnitRegistry registry = UnitRegistry.withDefaults();
        ConvertLengthUseCase useCase = new ConvertLengthUseCase(registry);
        System.out.println(useCase.renderPlain(args[0]));
    }
}
