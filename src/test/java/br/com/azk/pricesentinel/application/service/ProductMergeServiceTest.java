package br.com.azk.pricesentinel.application.service;

import br.com.azk.pricesentinel.application.service.normalization.ProductNormalizer;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductMergeServiceTest {

    @Mock
    private ProductNormalizer normalizer;

    private ProductMergeService service;

    @BeforeEach
    void setUp() {
        service = new ProductMergeService(normalizer);
    }

    @Test
    void shouldReturnEmptyList() {

        // Given
        List<ProductSearchResult> results = List.of();

        // When
        List<ProductSearchResult> merged = service.merge(results);

        // Then
        assertTrue(merged.isEmpty());

        verifyNoInteractions(normalizer);
    }

    @Test
    void shouldReturnSingleProduct() {

        // Given
        ProductSearchResult result = ProductSearchResult.builder()
                .name("Ryzen 7 5700X")
                .price(Money.of("999.90"))
                .build();

        // When
        List<ProductSearchResult> merged =
                service.merge(List.of(result));

        // Then
        assertEquals(1, merged.size());
        assertEquals(result, merged.getFirst());

        verifyNoInteractions(normalizer);
    }

    @Test
    void shouldSortProductsByPrice() {

        // Given
        ProductSearchResult expensive = ProductSearchResult.builder()
                .name("Produto A")
                .price(Money.of("1500.00"))
                .build();

        ProductSearchResult medium = ProductSearchResult.builder()
                .name("Produto B")
                .price(Money.of("1200.00"))
                .build();

        ProductSearchResult cheap = ProductSearchResult.builder()
                .name("Produto C")
                .price(Money.of("900.00"))
                .build();

        // When
        List<ProductSearchResult> merged = service.merge(
                List.of(expensive, cheap, medium)
        );

        // Then
        assertEquals(3, merged.size());

        assertEquals(cheap, merged.get(0));
        assertEquals(medium, merged.get(1));
        assertEquals(expensive, merged.get(2));

        verifyNoInteractions(normalizer);
    }

    @Test
    void shouldKeepProductsWithSamePrice() {

        // Given
        ProductSearchResult product1 = ProductSearchResult.builder()
                .name("Produto A")
                .price(Money.of("1000.00"))
                .build();

        ProductSearchResult product2 = ProductSearchResult.builder()
                .name("Produto B")
                .price(Money.of("1000.00"))
                .build();

        // When
        List<ProductSearchResult> merged =
                service.merge(List.of(product1, product2));

        // Then
        assertEquals(2, merged.size());

        verifyNoInteractions(normalizer);
    }

}