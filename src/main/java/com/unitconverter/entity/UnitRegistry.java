package com.unitconverter.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnitRegistry {

    private final Map<String, Double> metersPerOneUnit = new LinkedHashMap<>();

    public static UnitRegistry withDefaults() {
        UnitRegistry registry = new UnitRegistry();
        registry.register("meter", ConversionConstants.METERS_PER_METER);
        registry.register("feet", ConversionConstants.METERS_PER_FEET);
        registry.register("yard", ConversionConstants.METERS_PER_YARD);
        return registry;
    }

    public void register(String unitName, double metersPerOne) {
        metersPerOneUnit.put(unitName, metersPerOne);
    }

    public boolean contains(String unitName) {
        return metersPerOneUnit.containsKey(unitName);
    }

    public double getMetersPerOne(String unitName) {
        if (!contains(unitName)) {
            throw new DomainException("ERR-DOM-003", "Unknown unit \"" + unitName + "\".");
        }
        return metersPerOneUnit.get(unitName);
    }

    public List<String> getAllUnitNames() {
        return new ArrayList<>(metersPerOneUnit.keySet());
    }

    public int size() {
        return metersPerOneUnit.size();
    }

    public void clear() {
        metersPerOneUnit.clear();
    }

    public void putAll(Map<String, Double> units) {
        metersPerOneUnit.putAll(units);
    }
}
