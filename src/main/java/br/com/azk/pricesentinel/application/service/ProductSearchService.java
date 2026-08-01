package br.com.azk.pricesentinel.application.service;

import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.out.ProductSearchProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final List<ProductSearchProvider> providers;

    private final ProductMergeService mergeService;

    public List<ProductSearchResult> search(String query) {

        List<ProductSearchResult> results = new ArrayList<>();

        String normalizedQuery = normalizeQuery(query);

        for (ProductSearchProvider provider : providers) {
            searchInProvider(provider, normalizedQuery, results);
        }

        return mergeService.merge(results);
    }

    private void searchInProvider(
            ProductSearchProvider provider,
            String query,
            List<ProductSearchResult> results) {

        try {

            log.info(
                    "Pesquisando '{}' na loja {}",
                    query,
                    provider.getStore());

            results.addAll(provider.search(query));

        } catch (Exception ex) {

            log.error(
                    "Erro pesquisando '{}' na loja {}",
                    query,
                    provider.getStore(),
                    ex);

        }

    }

    private String normalizeQuery(String query) {

        // TODO Implementar normalização completa:
        // - remover múltiplos espaços
        // - remover acentos
        // - converter para minúsculas
        // - remover caracteres especiais
        // - Adicionar dentro da query a quantidade correta de itens na pesquisa
        // - Query condicional, não repetir mais de um produto (Da mesma loja com o mesmo preço)

        return query == null
                ? ""
                : query.trim();
    }

}