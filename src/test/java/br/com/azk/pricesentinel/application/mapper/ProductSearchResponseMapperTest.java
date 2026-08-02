package br.com.azk.pricesentinel.application.mapper;

import br.com.azk.pricesentinel.application.dto.response.ProductSearchResponse;
import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductSearchResponseMapperTest {

    private ProductSearchResponseMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new ProductSearchResponseMapper();

    }

    @Test
    void shouldMapProductSearchResultToResponse() {

        // Given
        ProductSearchResult result =
                ProductSearchResult.builder()
                        .name("Ryzen 7 5700X")
                        .price(Money.of("999.90"))
                        .store(Store.AMAZON)
                        .url("https://amazon.com.br/produto")
                        .build();

        // When
        ProductSearchResponse response =
                mapper.toResponse(result);

        // Then
        assertEquals("Ryzen 7 5700X", response.name());
        assertEquals("R$ 999,90", response.price());
        assertEquals("AMAZON", response.store());
        assertEquals(
                "https://amazon.com.br/produto",
                response.url());

    }

    @Test
    void shouldMapNullPrice() {

        // Given
        ProductSearchResult result =
                ProductSearchResult.builder()
                        .name("Ryzen")
                        .price(null)
                        .store(Store.AMAZON)
                        .url("url")
                        .build();

        // When
        ProductSearchResponse response =
                mapper.toResponse(result);

        // Then
        assertNull(response.price());

    }

    @Test
    void shouldMapStoreName() {

        // Given
        ProductSearchResult result =
                ProductSearchResult.builder()
                        .store(Store.AMAZON)
                        .build();

        // When
        ProductSearchResponse response =
                mapper.toResponse(result);

        // Then
        assertEquals(
                "AMAZON",
                response.store());

    }

    @Test
    void shouldMapUrl() {

        // Given
        ProductSearchResult result =
                ProductSearchResult.builder()
                        .url("https://teste.com")
                        .store(Store.AMAZON)
                        .build();

        // When
        ProductSearchResponse response =
                mapper.toResponse(result);

        // Then
        assertEquals(
                "https://teste.com",
                response.url());

    }

}