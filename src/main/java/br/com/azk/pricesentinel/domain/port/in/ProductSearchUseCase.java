package br.com.azk.pricesentinel.domain.port.in;

import br.com.azk.pricesentinel.domain.model.ProductSearchResult;

import java.util.List;

public interface ProductSearchUseCase {
    List<ProductSearchResult> search(String query);
}
