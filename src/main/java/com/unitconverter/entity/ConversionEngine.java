package com.unitconverter.entity;

import java.util.ArrayList;
import java.util.List;

public class ConversionEngine {

    private final UnitRegistry registry;

    public ConversionEngine(UnitRegistry registry) {
        this.registry = registry;
    }

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        double amountInMeters = sourceAmount * registry.getMetersPerOne(sourceUnit);
        return amountInMeters / registry.getMetersPerOne(targetUnit);
    }

    public List<ConversionResult> convertAll(String sourceUnit, double sourceAmount) {
        double amountInMeters = sourceAmount * registry.getMetersPerOne(sourceUnit);
        List<ConversionResult> results = new ArrayList<>();
        for (String targetUnit : registry.getAllUnitNames()) {
            double targetAmount = amountInMeters / registry.getMetersPerOne(targetUnit);
            results.add(new ConversionResult(sourceUnit, sourceAmount, targetUnit, targetAmount));
        }
        return results;
    }
}
