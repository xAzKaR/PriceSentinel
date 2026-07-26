package br.com.azk.pricesentinel.application.mapper;

import br.com.azk.pricesentinel.application.dto.response.ProductSearchResponse;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchResponseMapper {
    public ProductSearchResponse toResponse(ProductSearchResult result) {

        return ProductSearchResponse.builder()
                .name(result.getName())
                .price(result.getPrice() == null
                        ? null
                        : result.getPrice().toString())
                .store(result.getStore().name())
                .url(result.getUrl())
                .build();
    }
}
