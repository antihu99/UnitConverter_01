package com.unitconverter.entity;

public record ConversionResult(
        String sourceUnit,
        double sourceAmount,
        String targetUnit,
        double targetAmount) {
}
