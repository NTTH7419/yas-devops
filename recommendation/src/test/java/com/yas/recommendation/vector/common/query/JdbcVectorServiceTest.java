package com.yas.recommendation.vector.common.query;

import com.yas.recommendation.configuration.EmbeddingSearchConfiguration;
import com.yas.recommendation.vector.common.document.BaseDocument;
import com.yas.recommendation.vector.common.document.DocumentMetadata;
import com.yas.recommendation.vector.common.formatter.DefaultDocumentFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class JdbcVectorServiceTest {

    private JdbcTemplate jdbcClient;
    private ObjectMapper objectMapper;
    private EmbeddingSearchConfiguration config;
    private JdbcVectorService service;

    @DocumentMetadata(docIdPrefix = "TEST", contentFormat = "{content}", documentFormatter = DefaultDocumentFormatter.class)
    private static class TestDocument extends BaseDocument {}

    @BeforeEach
    void setUp() {
        jdbcClient = Mockito.mock(JdbcTemplate.class);
        objectMapper = new ObjectMapper();
        config = Mockito.mock(EmbeddingSearchConfiguration.class);
        service = new JdbcVectorService(jdbcClient, objectMapper, config);
    }

    @Test
    void similarityProduct_shouldCallJdbcTemplateWithCorrectUuid() {
        Long productId = 123L;
        service.similarityProduct(productId, TestDocument.class);

        verify(jdbcClient).query(any(String.class), any(org.springframework.jdbc.core.PreparedStatementSetter.class), any(DocumentRowMapper.class));
    }
}
