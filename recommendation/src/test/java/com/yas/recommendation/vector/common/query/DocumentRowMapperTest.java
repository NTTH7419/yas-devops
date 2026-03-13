package com.yas.recommendation.vector.common.query;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.document.Document;
import tools.jackson.databind.ObjectMapper;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DocumentRowMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DocumentRowMapper rowMapper = new DocumentRowMapper(objectMapper);

    @Test
    void mapRow_shouldReturnDocument() throws Exception {
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.getString(DocumentRowMapper.ID)).thenReturn("doc-1");
        when(rs.getString(DocumentRowMapper.CONTENT)).thenReturn("Sample content");
        when(rs.getObject(DocumentRowMapper.METADATA)).thenReturn("{\"key\":\"value\"}");

        Document document = rowMapper.mapRow(rs, 1);

        assertEquals("doc-1", document.getId());
        assertEquals("Sample content", document.getContent());
        assertEquals("value", document.getMetadata().get("key"));
    }
}
