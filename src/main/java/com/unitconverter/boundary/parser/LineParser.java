package com.unitconverter.boundary.parser;

public class LineParser {

    public ParsedInput parseConversionLine(String line) {
        if (line == null || !line.contains(":")) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
        String[] parts = line.split(":", 2);
        String unit = parts[0].trim();
        if (unit.isEmpty()) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
        try {
            double amount = Double.parseDouble(parts[1].trim());
            return new ParsedInput(unit, amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "ERROR [ERR-FMT-001]: Invalid input format. Expected \"unit:value\" or \"1 unit = X meter\". Input=\""
                            + line + "\"");
        }
    }
}
