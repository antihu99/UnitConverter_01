import com.unitconverter.boundary.output.PlainOutputRenderer;
import com.unitconverter.boundary.parser.InputValidator;
import com.unitconverter.boundary.parser.LineParser;
import com.unitconverter.boundary.parser.ParsedInput;
import com.unitconverter.data.config.ConfigurationLoader;
import com.unitconverter.entity.ConversionEngine;
import com.unitconverter.entity.ConversionResult;
import com.unitconverter.entity.UnitRegistry;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 테스트·GREEN용 얇은 파사드 — Domain/Boundary/Data BCE에 위임.
 */
public class UnitConverter {

    private final UnitRegistry registry;
    private final ConversionEngine engine;
    private final LineParser lineParser;
    private final InputValidator validator;
    private final PlainOutputRenderer plainOutputRenderer;
    private final ConfigurationLoader configurationLoader;

    public UnitConverter() {
        this(UnitRegistry.withDefaults());
    }

    UnitConverter(UnitRegistry registry) {
        this.registry = registry;
        this.engine = new ConversionEngine(registry);
        this.lineParser = new LineParser();
        this.validator = new InputValidator(registry);
        this.plainOutputRenderer = new PlainOutputRenderer();
        this.configurationLoader = new ConfigurationLoader();
    }

    public double convert(String sourceUnit, double sourceAmount, String targetUnit) {
        return engine.convert(sourceUnit, sourceAmount, targetUnit);
    }

    public void parse(String line) {
        ParsedInput input = lineParser.parseConversionLine(line);
        validator.validate(input);
    }

    public void registerUnit(String unitName, double metersPerOne) {
        registry.register(unitName, metersPerOne);
    }

    public List<ConversionRow> parseAndConvert(String line) {
        ParsedInput input = lineParser.parseConversionLine(line);
        validator.validate(input);
        return toConversionRows(engine.convertAll(input.unit(), input.amount()));
    }

    public String renderPlain(String line) {
        ParsedInput input = lineParser.parseConversionLine(line);
        validator.validate(input);
        return plainOutputRenderer.render(
                input.unit(), input.amount(), engine.convertAll(input.unit(), input.amount()));
    }

    public List<ConversionRow> convertAll(String sourceUnit, double sourceAmount) {
        return toConversionRows(engine.convertAll(sourceUnit, sourceAmount));
    }

    public void loadConfig(String path) {
        UnitRegistry loaded = configurationLoader.loadWithFallback(Paths.get(path));
        registry.clear();
        registry.putAll(loaded.snapshot());
    }

    private static List<ConversionRow> toConversionRows(List<ConversionResult> results) {
        List<ConversionRow> rows = new ArrayList<>();
        for (ConversionResult result : results) {
            rows.add(new ConversionRow(result.targetUnit(), result.targetAmount()));
        }
        return rows;
    }

    public static final class ConversionRow {
        public final String unit;
        public final double value;

        public ConversionRow(String unit, double value) {
            this.unit = unit;
            this.value = value;
        }
    }
}
