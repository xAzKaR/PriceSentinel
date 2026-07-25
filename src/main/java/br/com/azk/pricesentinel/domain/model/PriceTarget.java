package br.com.azk.pricesentinel.domain.model;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceTarget {

    private Product product;
    private Store store;
    private Money targetPrice;
}
