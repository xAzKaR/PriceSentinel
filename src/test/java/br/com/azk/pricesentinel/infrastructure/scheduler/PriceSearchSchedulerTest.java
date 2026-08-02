package br.com.azk.pricesentinel.infrastructure.scheduler;

import br.com.azk.pricesentinel.domain.port.in.PriceSearchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceSearchSchedulerTest {

    @Mock
    private PriceSearchUseCase priceSearchUseCase;

    private PriceSearchScheduler scheduler;

    @BeforeEach
    void setUp() {

        scheduler = new PriceSearchScheduler(
                priceSearchUseCase);

    }

    @Test
    void shouldExecutePriceSearch() {

        // When
        scheduler.execute();

        // Then
        verify(priceSearchUseCase)
                .search();

    }

    @Test
    void shouldContinueWhenUseCaseThrowsException() {

        doThrow(new RuntimeException())
                .when(priceSearchUseCase)
                .search();

        scheduler.execute();

        verify(priceSearchUseCase)
                .search();
    }

}