package com.yas.recommendation.vector.common.formatter;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultDocumentFormatterTest {

    private final DefaultDocumentFormatter formatter = new DefaultDocumentFormatter();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void format_shouldReplacePlaceholdersAndRemoveHtmlTags() {
        Map<String, Object> entityMap = new HashMap<>();
        entityMap.put("name", "Test Product");
        entityMap.put("desc", "<b>Great</b> product");
        
        String template = "Name: {name}, Description: {desc}";
        
        String result = formatter.format(entityMap, template, objectMapper);
        
        assertEquals("Name: Test Product, Description: Great product", result);
    }

    @Test
    void removeHtmlTags_shouldHandleNullAndEmpty() {
        assertEquals(null, formatter.removeHtmlTags(null));
        assertEquals("", formatter.removeHtmlTags(""));
    }

    @Test
    void removeHtmlTags_shouldRemoveTags() {
        assertEquals("Hello World", formatter.removeHtmlTags("<p>Hello</p> <span>World</span>"));
    }
}
