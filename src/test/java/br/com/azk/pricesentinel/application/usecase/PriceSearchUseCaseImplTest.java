package br.com.azk.pricesentinel.application.usecase;

import br.com.azk.pricesentinel.application.service.PriceSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceSearchUseCaseImplTest {

    @Mock
    private PriceSearchService service;

    private PriceSearchUseCaseImpl useCase;

    @BeforeEach
    void setUp() {

        useCase = new PriceSearchUseCaseImpl(service);

    }

    @Test
    void shouldDelegateSearchToService() {

        // When
        useCase.search();

        // Then
        verify(service).search();
        verifyNoMoreInteractions(service);

    }

    @Test
    void shouldPropagateException() {

        // Given
        doThrow(new RuntimeException("Erro"))
                .when(service)
                .search();

        // When / Then
        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> useCase.search());

        verify(service).search();

    }

}