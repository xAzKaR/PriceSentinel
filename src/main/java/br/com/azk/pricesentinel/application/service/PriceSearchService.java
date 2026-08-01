package br.com.azk.pricesentinel.application.service;


import br.com.azk.pricesentinel.domain.model.PriceResult;
import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.domain.port.out.NotificationChannel;
import br.com.azk.pricesentinel.domain.port.out.PriceTargetProvider;
import br.com.azk.pricesentinel.domain.port.out.StoreScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceSearchService {

    private final PriceTargetProvider targetProvider;
    private final List<StoreScraper> scrapers;
    private final List<NotificationChannel> notificationChannels;

    public void search() {

        List<PriceTarget> targets = targetProvider.findAll();

        for (PriceTarget target : targets) {

            log.info("Procurando preço para {}", target.getProduct().getName());

            for (StoreScraper scraper : scrapers) {
                searchInStore(target, scraper);
            }
        }
    }

    private void searchInStore(
            PriceTarget target,
            StoreScraper scraper) {

        try {

            scraper.search(target.getProduct())
                    .ifPresent(result -> processResult(target, result));

        } catch (Exception ex) {

            log.error(
                    "Erro pesquisando {} na loja {}",
                    target.getProduct().getName(),
                    scraper.getStore(),
                    ex);

        }
    }

    private void processResult(
            PriceTarget target,
            PriceResult result) {

        if (result.getPrice() == null || target.getTargetPrice() == null) {
            log.warn("Produto ignorado por não possuir preço.");
            return;
        }

//TODO Comentado temporariamente 31/07/2026
//        if (result.getPrice().isLessThanOrEqual(target.getTargetPrice())) {
//            notificationChannels.forEach(channel -> channel.send(result));
//        }

        notificationChannels.forEach(channel -> channel.send(result));
    }
}
