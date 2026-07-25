package br.com.azk.pricesentinel.infrastructure.provider;

import br.com.azk.pricesentinel.domain.port.out.PriceTargetProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

class PropertiesPriceTargetProviderTest {

    @Bean
    CommandLineRunner test(PriceTargetProvider provider) {

        return args -> provider.findAll()
                .forEach(System.out::println);

    }

}