package br.com.azk.pricesentinel.domain.model;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProductSearchResult {

    private final String name;

    private final Store store;

    private final Money price;

    private final String url;
}
