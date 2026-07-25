package br.com.azk.pricesentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceSentinelApplication.class, args);
    }

}
