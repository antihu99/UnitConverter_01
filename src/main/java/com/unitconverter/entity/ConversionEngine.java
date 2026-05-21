package com.unitconverter.entity;

import java.util.Collections;
import java.util.List;

/**
 * RED 단계: 최소 스텁 — GREEN 구현 전까지 테스트가 실패해야 함.
 */
public class ConversionEngine {

    private final UnitRegistry registry;

    public ConversionEngine(UnitRegistry registry) {
        this.registry = registry;
    }

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        // RED: meter→feet 등 환산 미구현 (항상 0 반환 → assertion FAIL)
        return 0.0;
    }

    public List<ConversionResult> convertAll(String sourceUnit, double sourceAmount) {
        // RED: 전 단위 변환 미구현
        return Collections.emptyList();
    }
}
