package br.com.azk.pricesentinel.application.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductSearchResultResponseTest {

    @Test
    void shouldCreateResponseUsingBuilder() {

        // Given
        ProductSearchResponse product =
                ProductSearchResponse.builder()
                        .name("Ryzen 7 5700X")
                        .price("R$ 999,90")
                        .store("AMAZON")
                        .url("https://amazon.com.br")
                        .build();

        // When
        ProductSearchResultResponse response =
                ProductSearchResultResponse.builder()
                        .query("Ryzen")
                        .total(1)
                        .products(List.of(product))
                        .build();

        // Then
        assertEquals("Ryzen", response.query());
        assertEquals(1, response.total());

        assertNotNull(response.products());
        assertEquals(1, response.products().size());

        assertEquals(
                "Ryzen 7 5700X",
                response.products().getFirst().name());

    }

    @Test
    void shouldCreateEmptyResponse() {

        // When
        ProductSearchResultResponse response =
                ProductSearchResultResponse.builder()
                        .query("Ryzen")
                        .total(0)
                        .products(List.of())
                        .build();

        // Then
        assertEquals("Ryzen", response.query());
        assertEquals(0, response.total());
        assertTrue(response.products().isEmpty());

    }

}