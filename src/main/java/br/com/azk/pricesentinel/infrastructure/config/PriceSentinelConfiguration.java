package br.com.azk.pricesentinel.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PriceSentinelProperties.class)
public class PriceSentinelConfiguration {
}
