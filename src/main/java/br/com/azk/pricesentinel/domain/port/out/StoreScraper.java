package br.com.azk.pricesentinel.domain.port.out;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.PriceResult;
import br.com.azk.pricesentinel.domain.model.Product;

import java.util.Optional;

public interface StoreScraper {

    Optional<PriceResult> search(Product product);

    Store getStore();
}
