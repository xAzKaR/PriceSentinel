package br.com.azk.pricesentinel.infrastructure.provider;

import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.port.out.PriceTargetProvider;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import br.com.azk.pricesentinel.infrastructure.config.PriceSentinelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PropertiesPriceTargetProvider implements PriceTargetProvider {

    private final PriceSentinelProperties properties;

    @Override
    public List<PriceTarget> findAll() {

        return properties.getProducts()
                .stream()
                .map(product -> PriceTarget.builder()
                        .product(
                                Product.builder()
                                        .id(product.getId())
                                        .name(product.getName())
                                        .build()
                        )
                        .targetPrice(Money.of(product.getTargetPrice()))
                        .build())
                .toList();
    }

}
