package br.com.azk.pricesentinel.infrastructure.scheduler;

import br.com.azk.pricesentinel.domain.port.in.PriceSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceSearchScheduler {

    private final PriceSearchUseCase priceSearchUseCase;

    @Scheduled(fixedDelayString = "${pricesentinel.scheduler.delay:60000}")
    public void execute() {

        log.info("Iniciando busca de preços...");

        priceSearchUseCase.search();

        log.info("Busca finalizada.");

    }
}
