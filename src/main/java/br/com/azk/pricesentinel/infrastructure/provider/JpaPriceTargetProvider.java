package br.com.azk.pricesentinel.infrastructure.provider;

import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.domain.port.out.PriceTargetProvider;
import br.com.azk.pricesentinel.infrastructure.persistence.mapper.PriceTargetEntityMapper;
import br.com.azk.pricesentinel.infrastructure.persistence.repository.PriceTargetJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Primary
@Slf4j
public class JpaPriceTargetProvider implements PriceTargetProvider {

    private final PriceTargetJpaRepository repository;
    private final PriceTargetEntityMapper mapper;


    @Override
    public List<PriceTarget> findAll() {
        List<PriceTarget> targets = repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();

        log.info("Foram encontrados {} produtos monitorados.", targets.size());
        return targets;
    }
}
