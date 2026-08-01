package br.com.azk.pricesentinel.application.dto.response;

import lombok.Builder;

@Builder
public record ProductSearchResponse(
        String name,
        String price,
        String store,
        String url
) {
}
