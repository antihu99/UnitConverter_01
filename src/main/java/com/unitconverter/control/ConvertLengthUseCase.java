package com.unitconverter.control;

import com.unitconverter.boundary.output.PlainOutputRenderer;
import com.unitconverter.boundary.parser.InputValidator;
import com.unitconverter.boundary.parser.LineParser;
import com.unitconverter.boundary.parser.ParsedInput;
import com.unitconverter.entity.ConversionEngine;
import com.unitconverter.entity.ConversionResult;
import com.unitconverter.entity.UnitRegistry;
import java.util.List;

/**
 * 길이 변환 유스케이스: 파싱 → 검증 → 변환 → PLAIN 렌더 (Control).
 */
public class ConvertLengthUseCase {

    private final LineParser lineParser;
    private final InputValidator inputValidator;
    private final ConversionEngine conversionEngine;
    private final PlainOutputRenderer plainOutputRenderer;

    public ConvertLengthUseCase(UnitRegistry registry) {
        this.lineParser = new LineParser();
        this.inputValidator = new InputValidator(registry);
        this.conversionEngine = new ConversionEngine(registry);
        this.plainOutputRenderer = new PlainOutputRenderer();
    }

    public ParsedInput parseAndValidate(String line) {
        ParsedInput input = lineParser.parseConversionLine(line);
        inputValidator.validate(input);
        return input;
    }

    public List<ConversionResult> convertAll(ParsedInput input) {
        return conversionEngine.convertAll(input.unit(), input.amount());
    }

    public String renderPlain(String line) {
        ParsedInput input = parseAndValidate(line);
        return plainOutputRenderer.render(
                input.unit(), input.amount(), convertAll(input));
    }
}
