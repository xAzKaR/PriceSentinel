package br.com.azk.pricesentinel.application.service;

import br.com.azk.pricesentinel.application.service.normalization.ProductNormalizer;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMergeService {

    private final ProductNormalizer normalizer;

    public List<ProductSearchResult> merge(List<ProductSearchResult> results) {


        return results.stream()
                .sorted(Comparator.comparing(ProductSearchResult::getPrice))
                .toList();
    }

}
