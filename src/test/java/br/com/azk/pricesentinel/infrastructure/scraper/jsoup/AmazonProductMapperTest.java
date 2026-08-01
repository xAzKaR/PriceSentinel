package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmazonProductMapperTest {

    private AmazonProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AmazonProductMapper();
    }

    @Test
    void shouldMapCompleteProduct() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <h2>
                        <a href="/produto">
                            <span>AMD Ryzen 7 5700X</span>
                        </a>
                    </h2>

                    <span class="a-price-whole">989</span>
                    <span class="a-price-fraction">90</span>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertNotNull(result);
        assertEquals("AMD Ryzen 7 5700X", result.getName());
        assertEquals("R$ 989,90", result.getPrice().toString());
        assertEquals(Store.AMAZON, result.getStore());
        assertEquals(
                "https://www.amazon.com.br/produto",
                result.getUrl());
    }

    @Test
    void shouldReturnNullWhenPriceDoesNotExist() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <h2>
                        <a href="/produto">
                            <span>Ryzen 7 5700X</span>
                        </a>
                    </h2>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertNull(result);
    }

    @Test
    void shouldReturnEmptyNameWhenTitleDoesNotExist() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <span class="a-price-whole">999</span>
                    <span class="a-price-fraction">90</span>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertNotNull(result);
        assertEquals("", result.getName());
    }

    @Test
    void shouldReturnEmptyUrlWhenLinkDoesNotExist() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <h2>
                        <span>Ryzen 7 5700X</span>
                    </h2>

                    <span class="a-price-whole">999</span>
                    <span class="a-price-fraction">90</span>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertNotNull(result);
        assertEquals("", result.getUrl());
    }

    @Test
    void shouldKeepAbsoluteUrl() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <h2>
                        <a href="https://amazon.com.br/produto">
                            <span>Ryzen 7 5700X</span>
                        </a>
                    </h2>

                    <span class="a-price-whole">999</span>
                    <span class="a-price-fraction">90</span>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertEquals(
                "https://amazon.com.br/produto",
                result.getUrl());
    }

    @Test
    void shouldNormalizePriceWithThousandsSeparator() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <h2>
                        <a href="/produto">
                            <span>Ryzen 9</span>
                        </a>
                    </h2>

                    <span class="a-price-whole">1.299</span>
                    <span class="a-price-fraction">90</span>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertNotNull(result);
        assertEquals("R$ 1.299,90", result.getPrice().toString());
    }

    @Test
    void shouldIgnoreInvalidPrice() {

        Element card = Jsoup.parse("""
                <div data-component-type="s-search-result">
                    <h2>
                        <a href="/produto">
                            <span>Ryzen 9</span>
                        </a>
                    </h2>

                    <span class="a-price-whole">ABC</span>
                    <span class="a-price-fraction">XX</span>
                </div>
                """).selectFirst("div");

        ProductSearchResult result = mapper.toProduct(card);

        assertNull(result);
    }

}