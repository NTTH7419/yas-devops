package com.yas.recommendation.vector.product.formatter;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDocumentFormatterTest {

    private final ProductDocumentFormatter formatter = new ProductDocumentFormatter();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void format_shouldFormatAttributesAndCategories() {
        Map<String, Object> entityMap = new HashMap<>();
        entityMap.put("name", "Laptop");

        List<Map<String, Object>> attributeValues = new ArrayList<>();
        attributeValues.add(Map.of("id", 1L, "nameProductAttribute", "RAM", "value", "16GB"));
        attributeValues.add(Map.of("id", 2L, "nameProductAttribute", "CPU", "value", "i7"));
        entityMap.put("attributeValues", attributeValues);

        List<Map<String, Object>> categories = new ArrayList<>();
        categories.add(Map.of("name", "Electronics"));
        categories.add(Map.of("name", "Computers"));
        entityMap.put("categories", categories);

        String template = "{name} | Attributes: {attributeValues} | Categories: {categories}";

        String result = formatter.format(entityMap, template, objectMapper);

        assertEquals("Laptop | Attributes: [RAM: 16GB, CPU: i7] | Categories: [Electronics, Computers]", result);
    }

    @Test
    void format_shouldHandleNulls() {
        Map<String, Object> entityMap = new HashMap<>();
        entityMap.put("name", "Laptop");
        entityMap.put("attributeValues", null);
        entityMap.put("categories", null);

        String template = "{name} | {attributeValues} | {categories}";

        String result = formatter.format(entityMap, template, objectMapper);

        assertEquals("Laptop | [] | []", result);
    }
}
