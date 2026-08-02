package br.com.azk.pricesentinel.infrastructure.provider;

import br.com.azk.pricesentinel.domain.model.PriceTarget;
import br.com.azk.pricesentinel.infrastructure.persistence.entity.PriceTargetEntity;
import br.com.azk.pricesentinel.infrastructure.persistence.mapper.PriceTargetEntityMapper;
import br.com.azk.pricesentinel.infrastructure.persistence.repository.PriceTargetJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaPriceTargetProviderTest {

    @Mock
    private PriceTargetJpaRepository repository;

    @Mock
    private PriceTargetEntityMapper mapper;

    private JpaPriceTargetProvider provider;

    @BeforeEach
    void setUp() {
        provider = new JpaPriceTargetProvider(
                repository,
                mapper);
    }

    @Test
    void shouldReturnAllPriceTargets() {

        // Given
        PriceTargetEntity entity1 = new PriceTargetEntity();
        entity1.setId(1L);
        entity1.setProductName("Ryzen 7 5700X");
        entity1.setTargetPrice(BigDecimal.valueOf(1000));

        PriceTargetEntity entity2 = new PriceTargetEntity();
        entity2.setId(2L);
        entity2.setProductName("Ryzen 7 5700X3D");
        entity2.setTargetPrice(BigDecimal.valueOf(1500));

        PriceTarget target1 = mock(PriceTarget.class);
        PriceTarget target2 = mock(PriceTarget.class);

        when(repository.findAll())
                .thenReturn(List.of(entity1, entity2));

        when(mapper.toDomain(entity1))
                .thenReturn(target1);

        when(mapper.toDomain(entity2))
                .thenReturn(target2);

        // When
        List<PriceTarget> result = provider.findAll();

        // Then
        assertEquals(2, result.size());
        assertSame(target1, result.getFirst());
        assertSame(target2, result.getLast());

        verify(repository).findAll();

        verify(mapper).toDomain(entity1);
        verify(mapper).toDomain(entity2);
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryIsEmpty() {

        // Given
        when(repository.findAll())
                .thenReturn(List.of());

        // When
        List<PriceTarget> result = provider.findAll();

        // Then
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldMapAllReturnedEntities() {

        // Given
        PriceTargetEntity entity1 = new PriceTargetEntity();
        PriceTargetEntity entity2 = new PriceTargetEntity();
        PriceTargetEntity entity3 = new PriceTargetEntity();

        when(repository.findAll())
                .thenReturn(List.of(entity1, entity2, entity3));

        when(mapper.toDomain(any()))
                .thenReturn(mock(PriceTarget.class));

        // When
        provider.findAll();

        // Then
        verify(mapper, times(3))
                .toDomain(any(PriceTargetEntity.class));
    }

}