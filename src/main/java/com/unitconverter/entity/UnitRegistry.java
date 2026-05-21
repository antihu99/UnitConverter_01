package com.unitconverter.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * RED 단계: 시드 단위·등록 로직 미구현.
 */
public class UnitRegistry {

    private final Map<String, Double> metersPerOneUnit = new LinkedHashMap<>();

    public static UnitRegistry withDefaults() {
        // RED: meter/feet/yard 시드 없음
        return new UnitRegistry();
    }

    public void register(String unitName, double metersPerOne) {
        // RED: 동적 등록 no-op
    }

    public boolean contains(String unitName) {
        return false;
    }

    public double getMetersPerOne(String unitName) {
        throw new DomainException("ERR-DOM-003", "Unknown unit \"" + unitName + "\".");
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
        // RED: 설정 로드 반영 미구현
    }
}
