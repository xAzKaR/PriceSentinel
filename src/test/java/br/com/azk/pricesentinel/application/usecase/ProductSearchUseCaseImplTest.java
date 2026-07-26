package br.com.azk.pricesentinel.application.usecase;

import br.com.azk.pricesentinel.application.service.ProductSearchService;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductSearchUseCaseImplTest {

    @Mock
    private ProductSearchService productSearchService;

    @InjectMocks
    private ProductSearchUseCaseImpl productSearchUseCase;

    private List<ProductSearchResult> results;

    @BeforeEach
    void setUp() {
        results = List.of(mock(ProductSearchResult.class));
    }

    @Test
    void shouldDelegateSearchToProductSearchService() {

        // Given
        String query = "Ryzen 7 5700X";

        when(productSearchService.search(query)).thenReturn(results);

        // When
        List<ProductSearchResult> response = productSearchUseCase.search(query);

        // Then
        assertSame(results, response);

        verify(productSearchService).search(query);
        verifyNoMoreInteractions(productSearchService);
    }

    @Test
    void shouldDelegateSearchWithEmptyQuery() {

        // Given
        String query = "";

        when(productSearchService.search(query)).thenReturn(List.of());

        // When
        List<ProductSearchResult> response = productSearchUseCase.search(query);

        // Then
        assertSame(List.of(), response);

        verify(productSearchService).search(query);
        verifyNoMoreInteractions(productSearchService);
    }

    @Test
    void shouldDelegateSearchWithNullQuery() {

        // Given
        when(productSearchService.search(null)).thenReturn(List.of());

        // When
        List<ProductSearchResult> response = productSearchUseCase.search(null);

        // Then
        assertSame(List.of(), response);

        verify(productSearchService).search(null);
        verifyNoMoreInteractions(productSearchService);
    }

}