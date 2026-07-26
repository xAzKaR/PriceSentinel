package br.com.azk.pricesentinel.infrastructure.provider;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FakeProductSearchProviderTest {

    private FakeProductSearchProvider provider;

    @BeforeEach
    void setUp() {
        provider = new FakeProductSearchProvider();
    }

    @Test
    void shouldReturnAmazonStore() {

        assertEquals(Store.AMAZON, provider.getStore());

    }

    @Test
    void shouldReturnThreeProducts() {

        List<ProductSearchResult> results = provider.search("Ryzen");

        assertEquals(3, results.size());

    }

    @Test
    void shouldReturnExpectedFirstProduct() {

        ProductSearchResult result = provider.search("Ryzen").get(0);

        assertAll(
                () -> assertEquals("AMD Ryzen 7 5700X", result.getName()),
                () -> assertEquals(Store.AMAZON, result.getStore()),
                () -> assertEquals("989.90", result.getPrice().value().toPlainString()),
                () -> assertEquals("https://amazon.com/fake/5700x", result.getUrl())
        );

    }

    @Test
    void shouldReturnExpectedSecondProduct() {

        ProductSearchResult result = provider.search("Ryzen").get(1);

        assertAll(
                () -> assertEquals("AMD Ryzen 7 5700X3D", result.getName()),
                () -> assertEquals(Store.AMAZON, result.getStore()),
                () -> assertEquals("1299.90", result.getPrice().value().toPlainString()),
                () -> assertEquals("https://amazon.com/fake/5700x3d", result.getUrl())
        );

    }

    @Test
    void shouldReturnExpectedThirdProduct() {

        ProductSearchResult result = provider.search("Ryzen").get(2);

        assertAll(
                () -> assertEquals("AMD Ryzen 5 5600", result.getName()),
                () -> assertEquals(Store.AMAZON, result.getStore()),
                () -> assertEquals("719.90", result.getPrice().value().toPlainString()),
                () -> assertEquals("https://amazon.com/fake/5600", result.getUrl())
        );

    }

    @Test
    void shouldIgnoreSearchQuery() {

        List<ProductSearchResult> firstSearch = provider.search("Ryzen");
        List<ProductSearchResult> secondSearch = provider.search("RTX 5070");
        List<ProductSearchResult> thirdSearch = provider.search(null);

        assertEquals(firstSearch, secondSearch);
        assertEquals(firstSearch, thirdSearch);

    }

}