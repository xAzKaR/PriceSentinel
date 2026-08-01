package br.com.azk.pricesentinel.application.service;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.out.ProductSearchProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductSearchServiceTest {

    @Mock
    private ProductSearchProvider amazonProvider;

    @Mock
    private ProductSearchProvider kabumProvider;

    @Mock
    private ProductMergeService mergeService;

    private ProductSearchService service;

    @BeforeEach
    void setUp() {

        service = new ProductSearchService(
                List.of(
                        amazonProvider,
                        kabumProvider
                ),
                mergeService
        );

        lenient().when(mergeService.merge(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldSearchInAllProviders() throws IOException {

        // Given
        ProductSearchResult amazonResult = mock(ProductSearchResult.class);
        ProductSearchResult kabumResult = mock(ProductSearchResult.class);

        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search("Ryzen"))
                .thenReturn(List.of(amazonResult));

        when(kabumProvider.search("Ryzen"))
                .thenReturn(List.of(kabumResult));

        // When
        List<ProductSearchResult> results = service.search("Ryzen");

        // Then
        assertEquals(2, results.size());
        assertEquals(amazonResult, results.get(0));
        assertEquals(kabumResult, results.get(1));

        verify(amazonProvider).search("Ryzen");
        verify(kabumProvider).search("Ryzen");
    }

    @Test
    void shouldTrimQueryBeforeSearching() throws IOException {

        // Given
        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search("Ryzen"))
                .thenReturn(List.of());

        when(kabumProvider.search("Ryzen"))
                .thenReturn(List.of());

        // When
        service.search("   Ryzen   ");

        // Then
        verify(amazonProvider).search("Ryzen");
        verify(kabumProvider).search("Ryzen");
    }

    @Test
    void shouldUseEmptyQueryWhenQueryIsNull() throws IOException {

        // Given
        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search(""))
                .thenReturn(List.of());

        when(kabumProvider.search(""))
                .thenReturn(List.of());

        // When
        service.search(null);

        // Then
        verify(amazonProvider).search("");
        verify(kabumProvider).search("");
    }

    @Test
    void shouldContinueWhenProviderThrowsException() throws IOException {

        // Given
        ProductSearchResult kabumResult = mock(ProductSearchResult.class);

        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search("Ryzen"))
                .thenThrow(new RuntimeException("Provider unavailable"));

        when(kabumProvider.search("Ryzen"))
                .thenReturn(List.of(kabumResult));

        // When
        List<ProductSearchResult> results = service.search("Ryzen");

        // Then
        assertEquals(1, results.size());
        assertEquals(kabumResult, results.getFirst());

        verify(amazonProvider).search("Ryzen");
        verify(kabumProvider).search("Ryzen");
    }

    @Test
    void shouldReturnEmptyListWhenProvidersReturnNothing() throws IOException {

        // Given
        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search("Ryzen"))
                .thenReturn(List.of());

        when(kabumProvider.search("Ryzen"))
                .thenReturn(List.of());

        // When
        List<ProductSearchResult> results = service.search("Ryzen");

        // Then
        assertEquals(0, results.size());

        verify(amazonProvider).search("Ryzen");
        verify(kabumProvider).search("Ryzen");
    }

    @Test
    void shouldReturnResultsFromSingleProvider() throws IOException {

        // Given
        ProductSearchResult amazonResult = mock(ProductSearchResult.class);

        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search("Ryzen"))
                .thenReturn(List.of(amazonResult));

        when(kabumProvider.search("Ryzen"))
                .thenReturn(List.of());

        when(mergeService.merge(anyList()))
                .thenReturn(List.of(amazonResult));

        // When
        List<ProductSearchResult> results = service.search("Ryzen");

        // Then
        assertEquals(1, results.size());
        assertEquals(amazonResult, results.getFirst());

        verify(amazonProvider).search("Ryzen");
        verify(kabumProvider).search("Ryzen");
        verify(mergeService).merge(anyList());
    }

    @Test
    void shouldDelegateResultsToMergeService2() throws IOException {

        ProductSearchResult amazonResult = mock(ProductSearchResult.class);

        when(amazonProvider.getStore()).thenReturn(Store.AMAZON);
        when(kabumProvider.getStore()).thenReturn(Store.KABUM);

        when(amazonProvider.search("Ryzen"))
                .thenReturn(List.of(amazonResult));

        when(kabumProvider.search("Ryzen"))
                .thenReturn(List.of());

        when(mergeService.merge(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.search("Ryzen");

        verify(mergeService).merge(argThat(list ->
                list.size() == 1 &&
                        list.contains(amazonResult)
        ));
    }

    private List<ProductSearchResult> mergeResults(
            List<ProductSearchResult> results) {

        return results.stream()
                .filter(Objects::nonNull)
                .toList();
    }
}