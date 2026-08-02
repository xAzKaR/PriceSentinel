package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.out.ProductSearchProvider;
import br.com.azk.pricesentinel.infrastructure.constants.StoreUrls;
import br.com.azk.pricesentinel.infrastructure.http.HtmlClient;
import br.com.azk.pricesentinel.infrastructure.http.UrlEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonSearchProvider implements ProductSearchProvider {

    private final HtmlClient htmlClient;

    private final AmazonProductMapper mapper;

    private static final String SEARCH_URL =
            "https://www.amazon.com.br/s?k=%s";

    @Override
    public Store getStore() {
        return Store.AMAZON;
    }

    @Override
    public List<ProductSearchResult> search(String query) throws IOException {

        String url = buildSearchUrl(query);

        Document document = htmlClient.get(url);

        if (log.isDebugEnabled()) {
            log.debug(document.outerHtml());
        }

        Elements cards = findProductsCards(document);

        log.info("Foram encontrados {} cards.", cards.size());

        return cards.stream()
                .map(mapper::toProduct)
                .filter(Objects::nonNull)
                .toList();
    }

    private String buildSearchUrl(String query) {

        String encodedQuery =
                UrlEncoder.encode(query);

        return StoreUrls.AMAZON.formatted(encodedQuery);

    }

    private Elements findProductsCards(Document document) {

        return document.select("[data-component-type=s-search-result]");

    }
}
