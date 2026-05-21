package com.unitconverter.data.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitsConfigDto {

    public String baseUnit;
    public List<UnitEntry> units;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UnitEntry {
        public String name;
        public double metersPerOneUnit;
    }
}
