package br.com.azk.pricesentinel.infrastructure.scraper;


import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.PriceResult;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.port.out.StoreScraper;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class FakeStoreScraperAdapter implements StoreScraper {
    @Override
    public Optional<PriceResult> search(Product product) {

        log.info("Pesquisando produto {}", product.getName());

        return Optional.of(
                PriceResult.builder()
                        .product(product)
                        .store(Store.AMAZON)
                        .price(Money.of("989.90"))
                        .url("https://www.amazon.com.br/fake")
                        .searchDate(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public Store getStore() {
        return Store.AMAZON;
    }
}
