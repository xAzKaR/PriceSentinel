package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmazonSearchProviderTest {

    private AmazonSearchProvider provider;

    @BeforeEach
    void setUp() {

        provider = new AmazonSearchProvider(
                new AmazonProductMapper());

    }

    @Test
    void shouldReturnAmazonStore() {

        assertEquals(
                Store.AMAZON,
                provider.getStore());

    }

    @Test
    void shouldBuildSearchUrl() {

        String url =
                provider.buildSearchUrl("Ryzen 7 5700X");

        assertEquals(
                "https://www.amazon.com.br/s?k=Ryzen+7+5700X",
                url);

    }

}