package br.com.azk.pricesentinel.infrastructure.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsoupClientTest {

    private final JsoupClient client =
            new JsoupClient();

    @Test
    void shouldReturnConfiguredUserAgent() {

        String userAgent = client.userAgent();

        assertEquals(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                        + "AppleWebKit/537.36 (KHTML, like Gecko) "
                        + "Chrome/138.0 Safari/537.36",
                userAgent);

    }

    @Test
    void shouldThrowRuntimeExceptionWhenUrlIsInvalid() {

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> client.get("http://url-inexistente.invalid"));

        assertTrue(
                exception.getMessage()
                        .contains("Erro ao conectar na URL"));

    }

}