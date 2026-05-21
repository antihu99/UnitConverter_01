package com.unitconverter.boundary.parser;

import com.unitconverter.entity.UnitRegistry;

/**
 * RED 단계: 검증 미구현 (음수·미등록 단위 통과).
 */
public class InputValidator {

    private final UnitRegistry registry;

    public InputValidator(UnitRegistry registry) {
        this.registry = registry;
    }

    public void validate(ParsedInput input) {
        // RED: no-op — 예외 테스트가 FAIL 되도록
    }
}
