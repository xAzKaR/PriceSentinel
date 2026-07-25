package br.com.azk.pricesentinel.infrastructure.scraper;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.PriceResult;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FakeStoreScraperAdapterTest {

    private FakeStoreScraperAdapter scraper;

    @BeforeEach
    void setUp() {
        scraper = new FakeStoreScraperAdapter();
    }

    @Test
    @DisplayName("Deve retornar a loja Amazon")
    void shouldReturnAmazonStore() {

        assertEquals(Store.AMAZON, scraper.getStore());
    }

    @Test
    @DisplayName("Deve retornar um resultado para o produto pesquisado")
    void shouldReturnPriceResult() {

        Product product = Product.builder()
                .name("Ryzen 7 5700X")
                .build();

        Optional<PriceResult> result = scraper.search(product);

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Deve preservar o produto informado")
    void shouldKeepOriginalProduct() {

        Product product = Product.builder()
                .name("Ryzen 7 5700X")
                .build();

        PriceResult result = scraper.search(product).orElseThrow();

        assertEquals(product, result.getProduct());
    }

    @Test
    @DisplayName("Deve retornar o preço esperado")
    void shouldReturnExpectedPrice() {

        Product product = Product.builder()
                .name("Ryzen 7 5700X")
                .build();

        PriceResult result = scraper.search(product).orElseThrow();

        assertEquals(Money.of("989.90"), result.getPrice());
    }

    @Test
    @DisplayName("Deve retornar a loja Amazon no resultado")
    void shouldReturnAmazonInPriceResult() {

        Product product = Product.builder()
                .name("Ryzen 7 5700X")
                .build();

        PriceResult result = scraper.search(product).orElseThrow();

        assertEquals(Store.AMAZON, result.getStore());
    }

    @Test
    @DisplayName("Deve retornar uma URL válida")
    void shouldReturnProductUrl() {

        Product product = Product.builder()
                .name("Ryzen 7 5700X")
                .build();

        PriceResult result = scraper.search(product).orElseThrow();

        assertEquals("https://www.amazon.com.br/fake", result.getUrl());
    }

    @Test
    @DisplayName("Deve preencher a data da pesquisa")
    void shouldFillSearchDate() {

        Product product = Product.builder()
                .name("Ryzen 7 5700X")
                .build();

        PriceResult result = scraper.search(product).orElseThrow();

        assertNotNull(result.getSearchDate());
    }

}