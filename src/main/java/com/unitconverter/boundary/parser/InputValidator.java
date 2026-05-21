package com.unitconverter.boundary.parser;

import com.unitconverter.entity.UnitRegistry;

public class InputValidator {

    private final UnitRegistry registry;

    public InputValidator(UnitRegistry registry) {
        this.registry = registry;
    }

    public void validate(ParsedInput input) {
        if (input.amount() < 0) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-VAL-002]: Length must be non-negative. Got " + input.amount() + ".");
        }
        if (!registry.contains(input.unit())) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-DOM-003]: Unknown unit \"" + input.unit() + "\".");
        }
    }
}
