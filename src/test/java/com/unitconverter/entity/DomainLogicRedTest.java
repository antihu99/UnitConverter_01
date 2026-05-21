package com.unitconverter.entity;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * TRACK B — Domain / Logic RED (Dual-Track).
 * BR-001: 1 meter = 3.28084 feet · BR-002: 1 meter = 1.09361 yard
 * 본문 구현 금지 — GREEN/REFACTOR 단계에서만 구현.
 */
@Tag("RED")
@DisplayName("[RED] Track B — Domain / Logic")
class DomainLogicRedTest {

    @Test
    @DisplayName("TC-B-RED-01: convert meter→feet (ε 1e-5)")
    void convert_meterToFeet_returnsCorrectRatio() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-B-RED-02: convert meter→yard (ε 1e-5)")
    void convert_meterToYard_returnsCorrectRatio() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-B-RED-03: convertAll — 모든 등록 단위")
    void convertAll_meterOne_returnsAllRegisteredUnits() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-B-RED-04: registerUnit cubit 후 변환")
    void registerUnit_cubit_thenConvertToMeter() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-B-RED-05: loadConfig valid JSON/YAML")
    void loadConfig_validFile_appliesRatios() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-B-RED-06: loadConfig missing — 기본값 유지")
    void loadConfig_missingFile_keepsDefaultRatios() {
        fail("RED");
    }
}
