package com.unitconverter.entity;

/**
 * BR-001: 1 meter = 3.28084 feet
 * BR-002: 1 meter = 1.09361 yard
 */
public final class ConversionConstants {

    public static final double METER_TO_FEET = 3.28084;
    public static final double METER_TO_YARD = 1.09361;
    public static final double METERS_PER_METER = 1.0;
    public static final double METERS_PER_FEET = 1.0 / METER_TO_FEET;
    public static final double METERS_PER_YARD = 1.0 / METER_TO_YARD;

    private ConversionConstants() {
    }
}
