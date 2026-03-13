package com.yas.recommendation.vector.common.document;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;

class DefaultIdGeneratorTest {

    @Test
    void generateId_shouldReturnConsistentUuid() {
        String prefix = "product";
        Long identity = 123L;
        DefaultIdGenerator generator = new DefaultIdGenerator(prefix, identity);

        String id = generator.generateId();
        
        assertNotNull(id);
        String expectedContent = "product-123";
        String expectedUuid = UUID.nameUUIDFromBytes(expectedContent.getBytes()).toString();
        assertEquals(expectedUuid, id);
    }
}
