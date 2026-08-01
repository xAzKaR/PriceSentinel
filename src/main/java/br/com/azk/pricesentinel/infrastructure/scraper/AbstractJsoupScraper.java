package br.com.azk.pricesentinel.infrastructure.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AbstractJsoupScraper {
    private static final int TIMEOUT = 10000;

    protected Document getDocument(String url) {
        try {
            log.info("Conectando à URL: {}", url);

            return createConnection(url).get();
        } catch (IOException ex) {

            throw new RuntimeException(
                    "Erro ao conectar na URL: " + url,
                    ex);
        }
    }

    protected Connection createConnection(String url) {
        return Jsoup.connect(url)
                .userAgent(userAgent())
                .timeout(TIMEOUT)
                .followRedirects(true)
                .ignoreHttpErrors(true);
    }

    protected String userAgent() {
        return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/138.0 Safari/537.36";
    }

    protected abstract String buildSearchUrl(String query);

    protected String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
