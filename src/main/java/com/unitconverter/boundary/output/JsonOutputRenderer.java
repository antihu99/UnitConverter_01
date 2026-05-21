package com.unitconverter.boundary.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unitconverter.entity.ConversionResult;
import java.util.List;

public class JsonOutputRenderer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String render(String sourceUnit, double sourceAmount, List<ConversionResult> results)
            throws JsonProcessingException {
        ObjectNode root = objectMapper.createObjectNode();
        ObjectNode source = root.putObject("source");
        source.put("unit", sourceUnit);
        source.put("amount", sourceAmount);
        ArrayNode conversions = root.putArray("conversions");
        for (ConversionResult result : results) {
            ObjectNode row = conversions.addObject();
            row.put("unit", result.targetUnit());
            row.put("amount", result.targetAmount());
        }
        return objectMapper.writeValueAsString(root);
    }
}
