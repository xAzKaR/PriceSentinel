package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.infrastructure.http.HtmlClient;
import br.com.azk.pricesentinel.infrastructure.http.UrlEncoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AmazonSearchProviderTest {

    @Mock
    private HtmlClient htmlClient;

    @Mock
    private AmazonProductMapper mapper;

    private AmazonSearchProvider provider;

    @BeforeEach
    void setUp() {

        provider = new AmazonSearchProvider(
                htmlClient,
                mapper);

    }

    @Test
    void shouldReturnAmazonStore() {

        assertEquals(
                Store.AMAZON,
                provider.getStore());

    }

    @Test
    void shouldCallHtmlClientWithEncodedUrl() throws IOException {

        Document document = Jsoup.parse("<html></html>");

        Mockito.when(htmlClient.get(Mockito.anyString()))
                .thenReturn(document);

        provider.search("Ryzen 7 5700X");

        Mockito.verify(htmlClient)
                .get("https://www.amazon.com.br/s?k=Ryzen+7+5700X");

    }

    @Test
    void shouldMapEveryCard() throws IOException {

        Document document = Jsoup.parse("""
        <div data-component-type="s-search-result"></div>
        <div data-component-type="s-search-result"></div>
    """);

        Mockito.when(htmlClient.get(Mockito.anyString()))
                .thenReturn(document);

        Mockito.when(mapper.toProduct(Mockito.any()))
                .thenReturn(Mockito.mock(ProductSearchResult.class));

        provider.search("Ryzen");

        Mockito.verify(mapper, Mockito.times(2))
                .toProduct(Mockito.any());

    }

    @Test
    void shouldIgnoreNullProducts() throws IOException {

        Document document = Jsoup.parse("""
        <div data-component-type="s-search-result"></div>
        <div data-component-type="s-search-result"></div>
    """);

        Mockito.when(htmlClient.get(Mockito.anyString()))
                .thenReturn(document);

        Mockito.when(mapper.toProduct(Mockito.any()))
                .thenReturn(Mockito.mock(ProductSearchResult.class))
                .thenReturn(null);

        List<ProductSearchResult> result =
                provider.search("Ryzen");

        assertEquals(1, result.size());

    }

}