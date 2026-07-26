package br.com.azk.pricesentinel.infrastructure.provider;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.out.ProductSearchProvider;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FakeProductSearchProvider implements ProductSearchProvider {

    @Override
    public Store getStore() {
        return Store.AMAZON;
    }

    @Override
    public List<ProductSearchResult> search(String query) {

        log.info("Pesquisando '{}' utilizando FakeProductSearchProvider", query);

        return List.of(

                ProductSearchResult.builder()
                        .name("AMD Ryzen 7 5700X")
                        .store(getStore())
                        .price(Money.of("989.90"))
                        .url("https://amazon.com/fake/5700x")
                        .build(),

                ProductSearchResult.builder()
                        .name("AMD Ryzen 7 5700X3D")
                        .store(getStore())
                        .price(Money.of("1299.90"))
                        .url("https://amazon.com/fake/5700x3d")
                        .build(),

                ProductSearchResult.builder()
                        .name("AMD Ryzen 5 5600")
                        .store(getStore())
                        .price(Money.of("719.90"))
                        .url("https://amazon.com/fake/5600")
                        .build()

        );
    }

}