package com.unitconverter.data.config;

import com.unitconverter.entity.UnitRegistry;
import java.io.IOException;
import java.nio.file.Path;

/**
 * RED 단계: JSON/YAML 로드 미구현.
 */
public class ConfigurationLoader {

    public UnitRegistry load(Path path) throws IOException {
        throw new IOException("RED: implement ConfigurationLoader.load");
    }

    public UnitRegistry loadWithFallback(Path path) {
        // RED: fallback도 빈 registry
        return UnitRegistry.withDefaults();
    }

    public UnitRegistry loadDefaults() {
        return UnitRegistry.withDefaults();
    }
}
