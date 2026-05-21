package com.unitconverter.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitconverter.entity.ConversionConstants;
import com.unitconverter.entity.UnitRegistry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigurationLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UnitRegistry load(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Config file not found: " + path);
        }
        UnitsConfigDto dto = objectMapper.readValue(path.toFile(), UnitsConfigDto.class);
        return toRegistry(dto);
    }

    public UnitRegistry loadWithFallback(Path path) {
        if (!Files.exists(path)) {
            return UnitRegistry.withDefaults();
        }
        try {
            return load(path);
        } catch (IOException e) {
            return UnitRegistry.withDefaults();
        }
    }

    public UnitRegistry loadDefaults() {
        return UnitRegistry.withDefaults();
    }

    private UnitRegistry toRegistry(UnitsConfigDto dto) {
        UnitRegistry registry = new UnitRegistry();
        registry.register("meter", ConversionConstants.METERS_PER_METER);
        if (dto.units != null) {
            for (UnitsConfigDto.UnitEntry entry : dto.units) {
                registry.register(entry.name, entry.metersPerOneUnit);
            }
        }
        return registry;
    }
}
