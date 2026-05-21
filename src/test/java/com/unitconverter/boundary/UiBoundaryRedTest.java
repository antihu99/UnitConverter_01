package com.unitconverter.boundary;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * TRACK A — UI / Boundary RED (Dual-Track).
 * 본문 구현 금지 — GREEN/REFACTOR 단계에서만 구현.
 */
@Tag("RED")
@DisplayName("[RED] Track A — UI / Boundary")
class UiBoundaryRedTest {

    @Test
    @DisplayName("TC-A-RED-01: meter:2.5 정상 입력 → 변환 결과 반환")
    void parseAndConvert_meterColonTwoFive_returnsConversionLines() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-A-RED-02: ':' 없는 입력 → IllegalArgumentException")
    void parse_missingColon_throwsIllegalArgumentException() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-A-RED-03: meter:-1.0 음수 → IllegalArgumentException")
    void validate_meterNegativeOne_throwsIllegalArgumentException() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-A-RED-04: parsec:1.0 없는 단위 → IllegalArgumentException")
    void validate_unknownUnit_parsec_throwsIllegalArgumentException() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-A-RED-05: PLAIN 출력 좌변 원입력 보존")
    void renderPlain_meterTwoFive_preservesSourceOnEveryLine() {
        fail("RED");
    }

    @Test
    @DisplayName("TC-A-RED-06: JSON 출력 스키마")
    void renderJson_meterTwoFive_matchesSchema() {
        fail("RED");
    }
}
