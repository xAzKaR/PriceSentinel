package br.com.azk.pricesentinel.infrastructure.controller.product;

import br.com.azk.pricesentinel.application.dto.response.ProductSearchResponse;
import br.com.azk.pricesentinel.application.mapper.ProductSearchResponseMapper;
import br.com.azk.pricesentinel.domain.port.in.ProductSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchUseCase productSearchUseCase;
    private final ProductSearchResponseMapper mapper;

    @GetMapping("/search")
    public List<ProductSearchResponse> search(
            @RequestParam String query) {

        return productSearchUseCase.search(query)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
