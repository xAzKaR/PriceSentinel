package br.com.azk.pricesentinel.infrastructure.scraper;

import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.PriceResult;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.port.out.StoreScraper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FakeStoreScraperAdapterContextTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(
                    FakeStoreScraperAdapter.class,
                    ScraperContextConfiguration.class);

    @Test
    void doesNotRegisterFakeScraperWhenPropertyIsAbsent() {
        contextRunner.run(context -> {
            assertThat(context).hasNotFailed();
            assertThat(context).doesNotHaveBean(FakeStoreScraperAdapter.class);
            assertThat(context.getBean(ScraperCollector.class).scrapers())
                    .containsExactly(context.getBean(SentinelStoreScraper.class));
        });
    }

    @Test
    void doesNotRegisterFakeScraperWhenPropertyIsFalse() {
        contextRunner
                .withPropertyValues("pricesentinel.scraper.fake.enabled=false")
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    assertThat(context).doesNotHaveBean(FakeStoreScraperAdapter.class);
                    assertThat(context.getBean(ScraperCollector.class).scrapers())
                            .containsExactly(context.getBean(SentinelStoreScraper.class));
                });
    }

    @Test
    void registersFakeScraperWhenExplicitlyEnabledOffline() {
        contextRunner
                .withPropertyValues("pricesentinel.scraper.fake.enabled=true")
                .withInitializer(context ->
                        context.getEnvironment().setActiveProfiles("offline"))
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    assertThat(context).hasSingleBean(FakeStoreScraperAdapter.class);
                    assertThat(context.getBean(ScraperCollector.class).scrapers())
                            .containsExactlyInAnyOrder(
                                    context.getBean(FakeStoreScraperAdapter.class),
                                    context.getBean(SentinelStoreScraper.class));
                });
    }

    @Test
    void doesNotRegisterFakeScraperWhenProdProfileIsActiveEvenIfEnabled() {
        contextRunner
                .withPropertyValues("pricesentinel.scraper.fake.enabled=true")
                .withInitializer(context ->
                        context.getEnvironment().setActiveProfiles("prod"))
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    assertThat(context).doesNotHaveBean(FakeStoreScraperAdapter.class);
                    assertThat(context.getBean(ScraperCollector.class).scrapers())
                            .containsExactly(context.getBean(SentinelStoreScraper.class));
                });
    }

    @Configuration(proxyBeanMethods = false)
    static class ScraperContextConfiguration {

        @Bean
        SentinelStoreScraper sentinelStoreScraper() {
            return new SentinelStoreScraper();
        }

        @Bean
        ScraperCollector scraperCollector(List<StoreScraper> scrapers) {
            return new ScraperCollector(scrapers);
        }
    }

    static class SentinelStoreScraper implements StoreScraper {

        @Override
        public Optional<PriceResult> search(Product product) {
            return Optional.empty();
        }

        @Override
        public Store getStore() {
            return Store.KABUM;
        }
    }

    record ScraperCollector(List<StoreScraper> scrapers) {
    }
}
