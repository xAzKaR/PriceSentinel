package br.com.azk.pricesentinel.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "pricesentinel")
public class PriceSentinelProperties {

    private Scheduler scheduler = new Scheduler();

    private Notification notification = new Notification();

    private List<ProductConfig> products = new ArrayList<>();

    @Getter
    @Setter
    public static class Scheduler {
        private boolean enabled;
        private String cron;
    }

    @Getter
    @Setter
    private static class Notification {

        private boolean enabled;
    }

    @Getter
    @Setter
    public static class ProductConfig {
        private String name;
        private BigDecimal targetPrice;
    }

}
