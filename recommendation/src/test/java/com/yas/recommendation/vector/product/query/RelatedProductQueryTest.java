package com.yas.recommendation.vector.product.query;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.vectorstore.VectorStore;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RelatedProductQueryTest {

    @Test
    void constructor_shouldWork() {
        VectorStore vectorStore = Mockito.mock(VectorStore.class);
        RelatedProductQuery query = new RelatedProductQuery(vectorStore);
        assertNotNull(query.getDocType());
        assertNotNull(query.getResultType());
    }
}
