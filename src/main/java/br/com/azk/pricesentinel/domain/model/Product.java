package br.com.azk.pricesentinel.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Product {

    private String id;
    private String name;
}
