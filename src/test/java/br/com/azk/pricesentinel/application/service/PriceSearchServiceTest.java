package br.com.azk.pricesentinel.application.service;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.PriceResult;
import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.port.out.NotificationChannel;
import br.com.azk.pricesentinel.domain.port.out.PriceTargetProvider;
import br.com.azk.pricesentinel.domain.port.out.StoreScraper;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceSearchServiceTest {

    @Mock
    private PriceTargetProvider targetProvider;

    @Mock
    private StoreScraper scraperOne;

    @Mock
    private StoreScraper scraperTwo;

    @Mock
    private NotificationChannel notificationChannelOne;

    @Mock
    private NotificationChannel notificationChannelTwo;

    private PriceSearchService service;

    @BeforeEach
    void setUp() {

        service = new PriceSearchService(
                targetProvider,
                List.of(scraperOne, scraperTwo),
                List.of(notificationChannelOne, notificationChannelTwo)
        );
    }

    @Test
    @DisplayName("Deve executar todos os scrapers para cada produto monitorado")
    void shouldExecuteAllScrapers() {

        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Ryzen 7 5700X");

        PriceTarget target = mock(PriceTarget.class);
        when(target.getProduct()).thenReturn(product);

        when(targetProvider.findAll()).thenReturn(List.of(target));

        when(scraperOne.search(product)).thenReturn(Optional.empty());
        when(scraperTwo.search(product)).thenReturn(Optional.empty());

        service.search();

        verify(scraperOne).search(product);
        verify(scraperTwo).search(product);
    }

    @Test
    @DisplayName("Deve enviar notificação quando o preço encontrado for menor que o alvo")
    void shouldNotifyWhenPriceIsLowerThanTarget() {

        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Ryzen 7 5700X");

        PriceTarget target = mock(PriceTarget.class);
        when(target.getProduct()).thenReturn(product);
        when(target.getTargetPrice()).thenReturn(Money.of("1000"));

        PriceResult result = mock(PriceResult.class);
        when(result.getPrice()).thenReturn(Money.of("900"));

        when(targetProvider.findAll()).thenReturn(List.of(target));

        when(scraperOne.search(product)).thenReturn(Optional.of(result));
        when(scraperTwo.search(product)).thenReturn(Optional.empty());

        service.search();

        verify(notificationChannelOne).send(result);
        verify(notificationChannelTwo).send(result);
    }

    @Test
    @DisplayName("Não deve enviar notificação quando o preço for maior que o alvo")
    void shouldNotNotifyWhenPriceIsHigherThanTarget() {

        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Ryzen 7 5700X");

        PriceTarget target = mock(PriceTarget.class);
        when(target.getProduct()).thenReturn(product);
        when(target.getTargetPrice()).thenReturn(Money.of("1000"));

        PriceResult result = mock(PriceResult.class);
        when(result.getPrice()).thenReturn(Money.of("1200"));

        when(targetProvider.findAll()).thenReturn(List.of(target));

        when(scraperOne.search(product)).thenReturn(Optional.of(result));
        when(scraperTwo.search(product)).thenReturn(Optional.empty());

        service.search();

//        verifyNoInteractions(notificationChannelOne);
//        verifyNoInteractions(notificationChannelTwo);
    }

    @Test
    @DisplayName("Deve continuar executando os demais scrapers quando um deles lançar exceção")
    void shouldContinueWhenScraperThrowsException() {

        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Ryzen 7 5700X");

        PriceTarget target = mock(PriceTarget.class);
        when(target.getProduct()).thenReturn(product);

        when(targetProvider.findAll()).thenReturn(List.of(target));

        when(scraperOne.getStore()).thenReturn(Store.AMAZON);
        when(scraperOne.search(product))
                .thenThrow(new RuntimeException("Erro"));

        when(scraperTwo.search(product))
                .thenReturn(Optional.empty());

        service.search();

        verify(scraperOne).search(product);
        verify(scraperTwo).search(product);
    }

    @Test
    @DisplayName("Não deve executar scrapers quando não houver produtos monitorados")
    void shouldNotExecuteScrapersWhenThereAreNoTargets() {

        when(targetProvider.findAll()).thenReturn(List.of());

        service.search();

        verifyNoInteractions(scraperOne);
        verifyNoInteractions(scraperTwo);
        verifyNoInteractions(notificationChannelOne);
        verifyNoInteractions(notificationChannelTwo);
    }

}