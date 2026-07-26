package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.out.ProductSearchProvider;
import br.com.azk.pricesentinel.infrastructure.scraper.AbstractJsoupScraper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonSearchProvider extends AbstractJsoupScraper implements ProductSearchProvider {

    private static AmazonProductMapper mapper;

    private static final String SEARCH_URL =
            "https://www.amazon.com.br/s?k=%s";

    @Override
    public Store getStore() {
        return Store.AMAZON;
    }

    @Override
    public List<ProductSearchResult> search(String query) throws IOException {

        String url = buildSearchUrl(query);

        Document document = getDocument(url);

        Elements cards = extractProductCards(document);

        log.info("Foram encontrados {} cards.", cards.size());

        return cards.stream()
                .map(mapper::toProduct)
                .toList();
    }

    @Override
    protected String buildSearchUrl(String query) {
        return SEARCH_URL.formatted(encode(query));
    }

    private Elements extractProductCards(Document document) {

        return document.select("[data-component-type=s-search-result]");

    }
}
