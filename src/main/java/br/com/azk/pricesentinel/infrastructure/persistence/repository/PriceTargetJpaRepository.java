package br.com.azk.pricesentinel.infrastructure.persistence.repository;

import br.com.azk.pricesentinel.infrastructure.persistence.entity.PriceTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTargetJpaRepository extends JpaRepository<PriceTargetEntity, Long> {
}
