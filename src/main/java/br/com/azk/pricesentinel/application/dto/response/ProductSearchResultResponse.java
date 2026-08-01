package br.com.azk.pricesentinel.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductSearchResultResponse(
        String query,
        Integer total,
        List<ProductSearchResponse> products
) {
}
