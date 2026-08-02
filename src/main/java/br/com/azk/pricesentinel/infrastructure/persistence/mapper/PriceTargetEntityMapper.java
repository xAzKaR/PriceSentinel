package br.com.azk.pricesentinel.infrastructure.persistence.mapper;

import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import br.com.azk.pricesentinel.infrastructure.persistence.entity.PriceTargetEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceTargetEntityMapper {

    public PriceTarget toDomain(PriceTargetEntity entity) {

        return PriceTarget.builder()
                .product(
                        Product.builder()
                                .id(String.valueOf(entity.getId()))
                                .name(entity.getProductName())
                                .build()
                )
                .targetPrice(Money.of(entity.getTargetPrice()))
                .build();
    }

    public PriceTargetEntity toEntity(PriceTarget target) {

        PriceTargetEntity entity = new PriceTargetEntity();

        if (target.getProduct().getId() != null) {
            entity.setId(Long.valueOf(target.getProduct().getId()));
        }

        entity.setProductName(target.getProduct().getName());

        entity.setTargetPrice(
                target.getTargetPrice().value());

        return entity;
    }
}
