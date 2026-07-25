package br.com.azk.pricesentinel.application.usecase;

import br.com.azk.pricesentinel.application.service.PriceSearchService;
import br.com.azk.pricesentinel.domain.port.in.PriceSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceSearchUseCaseImpl implements PriceSearchUseCase {

    private final PriceSearchService service;

    @Override
    public void search() {
        service.search();
    }
}
