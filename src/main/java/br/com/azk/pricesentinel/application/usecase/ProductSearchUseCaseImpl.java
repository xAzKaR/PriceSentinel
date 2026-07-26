package br.com.azk.pricesentinel.application.usecase;

import br.com.azk.pricesentinel.application.service.ProductSearchService;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.in.ProductSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductSearchUseCaseImpl implements ProductSearchUseCase {

    private final ProductSearchService productSearchService;

    @Override
    public List<ProductSearchResult> search(String query) {
        return productSearchService.search(query);
    }

}
