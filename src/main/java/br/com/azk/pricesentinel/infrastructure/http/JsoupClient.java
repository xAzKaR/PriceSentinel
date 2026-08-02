package br.com.azk.pricesentinel.infrastructure.http;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JsoupClient
        implements HtmlClient {

    private static final int TIMEOUT = 10000;

    @Override
    public Document get(String url) {

        try {
            log.info(
                    "Conectando à URL {}",
                    url);
            return createConnection(url).get();

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Erro ao conectar na URL " + url,
                    ex);

        }

    }

    Connection createConnection(String url) {

        return Jsoup.connect(url)
                .userAgent(userAgent())
                .timeout(TIMEOUT)
                .followRedirects(true)
                .ignoreHttpErrors(true);
    }

    String userAgent() {

        return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/138.0 Safari/537.36";

    }
}
