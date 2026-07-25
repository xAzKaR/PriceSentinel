package br.com.azk.pricesentinel.domain.port.out;

import br.com.azk.pricesentinel.domain.model.PriceTarget;

import java.util.List;

public interface PriceTargetProvider {

    List<PriceTarget> findAll();
}
