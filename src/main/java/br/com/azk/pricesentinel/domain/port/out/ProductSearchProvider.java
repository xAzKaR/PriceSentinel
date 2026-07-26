package br.com.azk.pricesentinel.domain.port.out;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;

import java.io.IOException;
import java.util.List;

public interface ProductSearchProvider {

    Store getStore();

    List<ProductSearchResult> search(String query) throws IOException;
}
