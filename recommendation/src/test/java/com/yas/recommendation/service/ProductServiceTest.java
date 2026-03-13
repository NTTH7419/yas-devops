package com.yas.recommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yas.recommendation.configuration.RecommendationConfig;
import com.yas.recommendation.viewmodel.ProductDetailVm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ProductServiceTest {

    private ProductService productService;
    private MockRestServiceServer mockServer;
    private RecommendationConfig config;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        config = Mockito.mock(RecommendationConfig.class);
        Mockito.when(config.getApiUrl()).thenReturn("http://api.yas.com");

        RestClient.Builder builder = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(builder).build();
        RestClient restClient = builder.build();

        productService = new ProductService(restClient, config);
    }

    @Test
    void getProductDetail_shouldReturnProductDetail_whenApiCallIsSuccessful() throws Exception {
        long productId = 1L;
        ProductDetailVm expectedProduct = new ProductDetailVm(
                productId, "Test Product", "Short", "Desc", "Spec", "SKU", "GTIN", "slug",
                true, true, true, true, true, 100.0, 1L, null, "MetaT", "MetaK", "MetaD",
                1L, "Brand", null, null, null, null
        );

        mockServer.expect(requestTo("http://api.yas.com/storefront/products/detail/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expectedProduct), MediaType.APPLICATION_JSON));

        ProductDetailVm actualProduct = productService.getProductDetail(productId);

        assertNotNull(actualProduct);
        assertEquals(expectedProduct.id(), actualProduct.id());
        assertEquals(expectedProduct.name(), actualProduct.name());
        mockServer.verify();
    }
}
