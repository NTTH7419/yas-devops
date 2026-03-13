package com.yas.recommendation.vector.common.document;

import com.yas.recommendation.vector.common.formatter.DefaultDocumentFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.id.IdGenerator;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BaseDocumentTest {

    @DocumentMetadata(docIdPrefix = "test", contentFormat = "{content}", documentFormatter = DefaultDocumentFormatter.class)
    private static class TestDocument extends BaseDocument {
    }

    private static class MissingAnnotationDocument extends BaseDocument {
    }

    @Test
    void toDocument_shouldReturnDocument_whenAnnotatedCorrectly() {
        TestDocument doc = new TestDocument();
        doc.setContent("Sample content");
        doc.setMetadata(new HashMap<>(Map.of("key", "value")));
        
        IdGenerator idGenerator = mock(IdGenerator.class);
        when(idGenerator.generateId(any())).thenReturn("doc-1");
        when(idGenerator.generateId(any(), any())).thenReturn("doc-1");

        Document result = doc.toDocument(idGenerator);

        assertNotNull(result);
        assertEquals("Sample content", result.getContent());
        assertEquals("value", result.getMetadata().get("key"));
    }

    @Test
    void toDocument_shouldThrowException_whenAnnotationIsMissing() {
        MissingAnnotationDocument doc = new MissingAnnotationDocument();
        doc.setContent("Sample content");
        doc.setMetadata(new HashMap<>());

        IdGenerator idGenerator = mock(IdGenerator.class);

        assertThrows(IllegalArgumentException.class, () -> doc.toDocument(idGenerator));
    }

    @Test
    void toDocument_shouldThrowException_whenContentIsNull() {
        TestDocument doc = new TestDocument();
        doc.setMetadata(new HashMap<>());

        IdGenerator idGenerator = mock(IdGenerator.class);

        assertThrows(IllegalArgumentException.class, () -> doc.toDocument(idGenerator));
    }
}
