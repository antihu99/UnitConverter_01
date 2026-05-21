package com.unitconverter.boundary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.unitconverter.boundary.output.JsonOutputRenderer;
import com.unitconverter.boundary.output.PlainOutputRenderer;
import com.unitconverter.boundary.parser.InputValidator;
import com.unitconverter.boundary.parser.LineParser;
import com.unitconverter.boundary.parser.ParsedInput;
import com.unitconverter.entity.ConversionEngine;
import com.unitconverter.entity.ConversionResult;
import com.unitconverter.entity.UnitRegistry;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Track A — UI / Boundary GREEN")
class UiBoundaryRedTest {

    private final LineParser parser = new LineParser();
    private final UnitRegistry registry = UnitRegistry.withDefaults();
    private final InputValidator validator = new InputValidator(registry);
    private final ConversionEngine engine = new ConversionEngine(registry);

    @Test
    @DisplayName("TC-A-RED-01 / TC-A-01: meter:2.5 정상 입력 → 변환 결과 반환")
    void parseAndConvert_meterColonTwoFive_returnsConversionLines() {
        ParsedInput input = parser.parseConversionLine("meter:2.5");
        validator.validate(input);
        List<ConversionResult> results = engine.convertAll(input.unit(), input.amount());
        assertTrue(results.size() >= 3);
    }

    @Test
    @DisplayName("TC-A-RED-02 / TC-A-02: ':' 없는 입력 → IllegalArgumentException")
    void parse_missingColon_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseConversionLine("meter"));
    }

    @Test
    @DisplayName("TC-A-RED-05b / TC-A-05: non-numeric amount → IllegalArgumentException")
    void parse_nonNumericAmount_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseConversionLine("meter:abc"));
    }

    @Test
    @DisplayName("TC-A-RED-02b: empty unit name → IllegalArgumentException")
    void parse_emptyUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseConversionLine(":2.5"));
    }

    @Test
    @DisplayName("TC-A-RED-03 / TC-A-03: meter:-1.0 음수 → IllegalArgumentException")
    void validate_meterNegativeOne_throwsIllegalArgumentException() {
        ParsedInput input = parser.parseConversionLine("meter:-1.0");
        assertThrows(IllegalArgumentException.class, () -> validator.validate(input));
    }

    @Test
    @DisplayName("TC-A-RED-04 / TC-A-04: parsec:1.0 없는 단위 → IllegalArgumentException")
    void validate_unknownUnit_parsec_throwsIllegalArgumentException() {
        ParsedInput input = parser.parseConversionLine("parsec:1.0");
        assertThrows(IllegalArgumentException.class, () -> validator.validate(input));
    }

    @Test
    @DisplayName("TC-A-RED-05 / TC-A-06: PLAIN 출력 좌변 원입력 보존")
    void renderPlain_meterTwoFive_preservesSourceOnEveryLine() throws Exception {
        ParsedInput input = parser.parseConversionLine("meter:2.5");
        validator.validate(input);
        List<ConversionResult> results = engine.convertAll(input.unit(), input.amount());
        String output = new PlainOutputRenderer().render(input.unit(), input.amount(), results);
        assertTrue(output.contains("2.5 meter"));
        assertTrue(output.contains("8.2 feet"));
    }

    @Test
    @DisplayName("TC-A-RED-06: JSON 출력 스키마")
    void renderJson_meterTwoFive_matchesSchema() throws Exception {
        ParsedInput input = parser.parseConversionLine("meter:2.5");
        validator.validate(input);
        List<ConversionResult> results = engine.convertAll(input.unit(), input.amount());
        String json = new JsonOutputRenderer().render(input.unit(), input.amount(), results);
        assertTrue(json.contains("\"source\""));
        assertTrue(json.contains("\"conversions\""));
        assertTrue(json.contains("\"unit\":\"meter\""));
    }
}
