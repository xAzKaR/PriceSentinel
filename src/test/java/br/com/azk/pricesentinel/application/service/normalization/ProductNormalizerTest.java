package br.com.azk.pricesentinel.application.service.normalization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductNormalizerTest {

    private ProductNormalizer normalizer;

    @BeforeEach
    void setUp() {
        normalizer = new ProductNormalizer();
    }

    @Test
    void shouldRemoveProcessorWord() {

        String result = normalizer.normalize(
                "Processador AMD Ryzen 7 5700X");

        assertEquals("Ryzen 7 5700X", result);
    }

    @Test
    void shouldRemoveTrademark() {

        String result =
                normalizer.normalize("AMD Ryzen™ 7 5700X");

        assertEquals("Ryzen 7 5700X", result);
    }

    @Test
    void shouldHandleNull() {

        assertEquals("", normalizer.normalize(null));
    }

    @Test
    void shouldHandleBlank() {

        assertEquals("", normalizer.normalize(" "));
    }

}