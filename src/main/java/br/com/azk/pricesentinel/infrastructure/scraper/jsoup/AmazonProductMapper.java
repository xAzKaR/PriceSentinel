package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class AmazonProductMapper {

    public ProductSearchResult toProduct(Element card) {

        String name = extractName(card);

        String url = extractUrl(card);

        Money price = extractPrice(card);

        return ProductSearchResult.builder()
                .name(name)
                .price(price)
                .url(url)
                .store(Store.AMAZON)
                .build();

    }


    private String extractName(Element card) {

        Element title = card.selectFirst("h2 span");

        if (title == null) {
            return "";
        }

        return title.text().trim();
    }


    private Money extractPrice(Element card) {

        String text = extractPriceText(card);

        if (text.isBlank()) {
            return null;
        }

        return Money.of(normalizePrice(text));

    }


    private String extractUrl(Element card) {

        Element link = card.selectFirst("h2 a");

        if (link == null) {
            return "";
        }

        String href = link.attr("href");

        return "https://www.amazon.com.br" + href;

    }


    private String extractPriceText(Element card) {

        Element whole = card.selectFirst(".a-price-whole");
        Element fraction = card.selectFirst(".a-price-fraction");

        if (whole == null || fraction == null) {
            return "";
        }

        return whole.text() + "," + fraction.text();

    }


    private String normalizePrice(String price) {

        return price
                .replace(".", "")
                .replace(",", ".")
                .trim();

    }
}
