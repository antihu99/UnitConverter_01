package com.unitconverter.entity;

/**
 * PLAIN 출력용 소수 1자리 half-up 정책 (PRD §3.2, §6.1).
 */
public final class RoundingPolicy {

    private RoundingPolicy() {
    }

    public static double toOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
