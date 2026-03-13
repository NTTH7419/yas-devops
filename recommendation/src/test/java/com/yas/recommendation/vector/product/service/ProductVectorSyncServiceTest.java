package com.yas.recommendation.vector.product.service;

import com.yas.commonlibrary.kafka.cdc.message.Product;
import com.yas.recommendation.vector.product.store.ProductVectorRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ProductVectorSyncServiceTest {

    private final ProductVectorRepository repository = mock(ProductVectorRepository.class);
    private final ProductVectorSyncService service = new ProductVectorSyncService(repository);

    @Test
    void createProductVector_shouldAdd_whenPublished() {
        Product product = mock(Product.class);
        when(product.isPublished()).thenReturn(true);
        when(product.getId()).thenReturn(1L);

        service.createProductVector(product);

        verify(repository).add(1L);
    }

    @Test
    void createProductVector_shouldNotAdd_whenNotPublished() {
        Product product = mock(Product.class);
        when(product.isPublished()).thenReturn(false);

        service.createProductVector(product);

        verify(repository, never()).add(anyLong());
    }

    @Test
    void updateProductVector_shouldUpdate_whenPublished() {
        Product product = mock(Product.class);
        when(product.isPublished()).thenReturn(true);
        when(product.getId()).thenReturn(1L);

        service.updateProductVector(product);

        verify(repository).update(1L);
    }

    @Test
    void updateProductVector_shouldDelete_whenNotPublished() {
        Product product = mock(Product.class);
        when(product.isPublished()).thenReturn(false);
        when(product.getId()).thenReturn(1L);

        service.updateProductVector(product);

        verify(repository).delete(1L);
    }

    @Test
    void deleteProductVector_shouldDelete() {
        service.deleteProductVector(1L);
        verify(repository).delete(1L);
    }
}
