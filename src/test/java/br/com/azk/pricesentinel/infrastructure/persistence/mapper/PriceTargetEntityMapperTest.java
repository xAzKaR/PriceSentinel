package br.com.azk.pricesentinel.infrastructure.persistence.mapper;

import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.domain.model.Product;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import br.com.azk.pricesentinel.infrastructure.persistence.entity.PriceTargetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceTargetEntityMapperTest {

    private PriceTargetEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PriceTargetEntityMapper();
    }

    @Test
    void shouldConvertEntityToDomain() {

        // Given
        PriceTargetEntity entity = new PriceTargetEntity();
        entity.setId(1L);
        entity.setProductName("Ryzen 7 5700X");
        entity.setTargetPrice(BigDecimal.valueOf(1000.00));

        // When
        PriceTarget target = mapper.toDomain(entity);

        // Then
        assertNotNull(target);

        assertEquals("1", target.getProduct().getId());
        assertEquals("Ryzen 7 5700X", target.getProduct().getName());

        assertEquals(
                0,
                Money.of("1000.00")
                        .value()
                        .compareTo(target.getTargetPrice().value()));
    }

    @Test
    void shouldConvertDomainToEntity() {

        // Given
        PriceTarget target = PriceTarget.builder()
                .product(
                        Product.builder()
                                .id("10")
                                .name("Ryzen 7 5700X")
                                .build())
                .targetPrice(Money.of("999.90"))
                .build();

        // When
        PriceTargetEntity entity = mapper.toEntity(target);

        // Then
        assertNotNull(entity);

        assertEquals(10L, entity.getId());
        assertEquals("Ryzen 7 5700X", entity.getProductName());

        assertBigDecimalEquals(
                BigDecimal.valueOf(999.90),
                entity.getTargetPrice());
    }

    @Test
    void shouldConvertDomainWithoutId() {

        // Given
        PriceTarget target = PriceTarget.builder()
                .product(
                        Product.builder()
                                .name("Ryzen 5 5600")
                                .build())
                .targetPrice(Money.of("750.00"))
                .build();

        // When
        PriceTargetEntity entity = mapper.toEntity(target);

        // Then
        assertNull(entity.getId());

        assertEquals(
                "Ryzen 5 5600",
                entity.getProductName());

        assertBigDecimalEquals(
                BigDecimal.valueOf(750.00),
                entity.getTargetPrice());
    }

    @Test
    void shouldKeepProductNameExactlyAsReceived() {

        // Given
        PriceTargetEntity entity = new PriceTargetEntity();
        entity.setId(5L);
        entity.setProductName(" AMD Ryzen 7 5700X ");
        entity.setTargetPrice(BigDecimal.valueOf(1000));

        // When
        PriceTarget target = mapper.toDomain(entity);

        // Then
        assertEquals(
                " AMD Ryzen 7 5700X ",
                target.getProduct().getName());
    }

    private void assertBigDecimalEquals(
            BigDecimal expected,
            BigDecimal actual) {

        assertEquals(
                0,
                expected.compareTo(actual));
    }
}