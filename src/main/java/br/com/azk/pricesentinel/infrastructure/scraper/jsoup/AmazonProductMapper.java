package br.com.azk.pricesentinel.infrastructure.scraper.jsoup;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmazonProductMapper {

    public ProductSearchResult toProduct(Element card) {

        String name = extractName(card);

        String url = extractUrl(card);

        Money price = extractPrice(card);

        if (price == null) {
            log.debug("Card ignorado por não possuir preço");
            return null;
        }

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
            log.debug("Card ignorado por não possuir nome");
            return "";
        }

        return title.text().trim();
    }


    private Money extractPrice(Element card) {

        String text = extractPriceText(card);
        log.info("Preço bruto: '{}'", text);

        if (text.isBlank()) {
            return null;
        }

        String normalized = normalizePrice(text);
        log.info("Preço normalizado: '{}'", normalized);

        try {
            return Money.of(normalized);
        } catch (Exception ex) {
            log.error(
                    "Erro convertendo preço bruto='{}' normalizado='{}'",
                    text,
                    normalized,
                    ex);
            return null;
        }

    }


    private String extractUrl(Element card) {

        Element link = card.selectFirst("a[href]");

        if (link == null) {
            return "";
        }

        String href = link.attr("href");

        if (!href.startsWith("/")) {
            return href;
        }

        return "https://www.amazon.com.br" + href;
    }


    private String extractPriceText(Element card) {

        Element whole = card.selectFirst(".a-price-whole");
        Element fraction = card.selectFirst(".a-price-fraction");

        if (whole == null || fraction == null) {
            return "";
        }

        String integer = whole.text()
                .replaceAll("[^0-9]", "");

        String decimal = fraction.text()
                .replaceAll("[^0-9]", "");

        return integer + "," + decimal;
    }


    private String normalizePrice(String price) {

        return price
                .replace(".", "")
                .replace(",", ".")
                .trim();

    }
}
