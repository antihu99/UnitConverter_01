package com.unitconverter.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.unitconverter.data.config.ConfigurationLoader;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Track B — Domain / Logic GREEN")
class DomainLogicRedTest {

    @Test
    @DisplayName("TC-B-RED-01 / TC-B-01: convert meter→feet (ε 1e-5)")
    void convert_meterToFeet_returnsCorrectRatio() {
        ConversionEngine engine = new ConversionEngine(UnitRegistry.withDefaults());
        assertEquals(8.20210, engine.convert("meter", 2.5, "feet"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-RED-02 / TC-B-02: convert meter→yard (ε 1e-5)")
    void convert_meterToYard_returnsCorrectRatio() {
        ConversionEngine engine = new ConversionEngine(UnitRegistry.withDefaults());
        assertEquals(1.09361, engine.convert("meter", 1.0, "yard"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-RED-03b / TC-B-03: convert feet→meter (ε 1e-5)")
    void convert_feetToMeter_returnsCorrectRatio() {
        ConversionEngine engine = new ConversionEngine(UnitRegistry.withDefaults());
        assertEquals(0.30480, engine.convert("feet", 1.0, "meter"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-RED-03 / TC-B-04: convertAll — 모든 등록 단위")
    void convertAll_meterOne_returnsAllRegisteredUnits() {
        ConversionEngine engine = new ConversionEngine(UnitRegistry.withDefaults());
        assertEquals(3, engine.convertAll("meter", 1.0).size());
    }

    @Test
    @DisplayName("TC-B-RED-04 / TC-B-05: registerUnit cubit 후 변환")
    void registerUnit_cubit_thenConvertToMeter() {
        UnitRegistry registry = UnitRegistry.withDefaults();
        registry.register("cubit", 0.4572);
        ConversionEngine engine = new ConversionEngine(registry);
        assertEquals(0.4572, engine.convert("cubit", 1.0, "meter"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-RED-05 / TC-B-06: loadConfig valid JSON")
    void loadConfig_validFile_appliesRatios() {
        ConfigurationLoader loader = new ConfigurationLoader();
        UnitRegistry registry = loader.loadWithFallback(Path.of("src/test/resources/units-valid.json"));
        ConversionEngine engine = new ConversionEngine(registry);
        assertEquals(3.28084, engine.convert("meter", 1.0, "feet"), 1e-4);
    }

    @Test
    @DisplayName("UnitRegistry: unknown unit throws DomainException")
    void registry_unknownUnit_throwsDomainException() {
        UnitRegistry registry = UnitRegistry.withDefaults();
        assertThrows(DomainException.class, () -> registry.getMetersPerOne("parsec"));
    }

    @Test
    @DisplayName("UnitRegistry: size, clear, putAll")
    void registry_sizeClearPutAll() {
        UnitRegistry registry = new UnitRegistry();
        assertEquals(0, registry.size());
        registry.putAll(Map.of("meter", 1.0, "feet", 0.3048));
        assertEquals(2, registry.size());
        registry.clear();
        assertEquals(0, registry.size());
    }

    @Test
    @DisplayName("TC-B-RED-06 / TC-B-07: loadConfig missing — 기본값 유지")
    void loadConfig_missingFile_keepsDefaultRatios() {
        ConfigurationLoader loader = new ConfigurationLoader();
        UnitRegistry registry = loader.loadWithFallback(Path.of("src/test/resources/units-missing.json"));
        ConversionEngine engine = new ConversionEngine(registry);
        assertEquals(
                1.0 * ConversionConstants.METER_TO_FEET,
                engine.convert("meter", 1.0, "feet"),
                1e-5);
    }
}
