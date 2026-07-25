package br.com.azk.pricesentinel.domain.model;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductSearchResult {

    private Product product;

    private Store store;

    private Money price;

    private String url;
}
