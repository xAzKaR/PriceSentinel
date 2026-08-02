package br.com.azk.pricesentinel.domain.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("Deve criar um Money a partir de uma String")
    void shouldCreateMoneyFromString() {

        Money money = Money.of("10.50");

        assertEquals(new BigDecimal("10.50"), money.value());
    }

    @Test
    @DisplayName("Deve criar um Money a partir de um BigDecimal")
    void shouldCreateMoneyFromBigDecimal() {

        Money money = Money.of(new BigDecimal("20.30"));

        assertEquals(new BigDecimal("20.30"), money.value());
    }

    @Test
    @DisplayName("Deve retornar um Money com valor zero")
    void shouldReturnZero() {

        Money money = Money.zero();

        assertEquals(BigDecimal.ZERO, money.value());
    }

    @Test
    @DisplayName("Deve somar dois valores")
    void shouldAddValues() {

        Money result = Money.of("10.00")
                .add(Money.of("5.00"));

        assertEquals(new BigDecimal("15.00"), result.value());
    }

    @Test
    @DisplayName("Deve subtrair dois valores")
    void shouldSubtractValues() {

        Money result = Money.of("10.00")
                .subtract(Money.of("3.00"));

        assertEquals(new BigDecimal("7.00"), result.value());
    }

    @Test
    @DisplayName("Deve retornar o valor absoluto")
    void shouldReturnAbsoluteValue() {

        Money result = Money.of("-15.50").abs();

        assertEquals(new BigDecimal("15.50"), result.value());
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando o valor for menor")
    void shouldReturnTrueWhenValueIsLessThanOther() {

        Money first = Money.of("10.00");
        Money second = Money.of("20.00");

        assertTrue(first.isLessThanOrEqual(second));
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando os valores forem iguais")
    void shouldReturnTrueWhenValuesAreEqual() {

        Money first = Money.of("20.00");
        Money second = Money.of("20.00");

        assertTrue(first.isLessThanOrEqual(second));
    }

    @Test
    @DisplayName("Deve retornar falso quando o valor for maior")
    void shouldReturnFalseWhenValueIsGreater() {

        Money first = Money.of("30.00");
        Money second = Money.of("20.00");

        assertFalse(first.isLessThanOrEqual(second));
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando o valor for maior")
    void shouldReturnTrueWhenValueIsGreaterThanOther() {

        Money first = Money.of("30.00");
        Money second = Money.of("20.00");

        assertTrue(first.isGreaterThan(second));
    }

    @Test
    @DisplayName("Deve retornar falso quando o valor não for maior")
    void shouldReturnFalseWhenValueIsNotGreaterThanOther() {

        Money first = Money.of("10.00");
        Money second = Money.of("20.00");

        assertFalse(first.isGreaterThan(second));
    }

    @Test
    @DisplayName("Deve formatar o valor em moeda brasileira")
    void shouldFormatToBrazilianCurrency() {

        Money money = Money.of("1234.56");

        assertEquals("R$ 1.234,56", money.toString());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o valor for nulo")
    void shouldThrowExceptionWhenValueIsNull() {

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Money(null)
        );

        assertEquals("Money cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar o valor correto a partir de um double")
    void shouldCreateMoneyFromDouble() {

        // When
        Money money = Money.of(999.90);

        // Then
        assertEquals(
                BigDecimal.valueOf(999.90),
                money.value());

    }

    @Test
    @DisplayName("Deve criar o valor correto a partir de um long")
    void shouldCreateMoneyFromLong() {

        // When
        Money money = Money.of(1500L);

        // Then
        assertEquals(
                BigDecimal.valueOf(1500L),
                money.value());

    }

    @Test
    @DisplayName("Deve validar o valor corretamente de equals")
    void shouldBeEqualToItself() {

        Money money = Money.of("1000.00");

        assertEquals(money, money);

    }

    @Test
    @DisplayName("Deve ser igual com valores com escalas diferentes")
    void shouldBeEqualWhenScaleIsDifferent() {

        Money first = Money.of("1000.0");
        Money second = Money.of("1000.00");

        assertEquals(first, second);

    }

    @Test
    @DisplayName("Não dedve ser igual para valores diferentes")
    void shouldNotBeEqualWhenValuesAreDifferent() {

        Money first = Money.of("1000.00");
        Money second = Money.of("999.99");

        assertNotEquals(first, second);

    }

    @Test
    @DisplayName("Não deve ser null")
    void shouldNotBeEqualToNull() {

        Money money = Money.of("1000.00");

        assertNotEquals(null, money);

    }

    @Test
    @DisplayName("Não deve ser igual a outro tipo")
    void shouldNotBeEqualToDifferentType() {

        Money money = Money.of("1000.00");

        assertNotEquals("1000.00", money);

    }

    @Test
    @DisplayName("HashCode deve ser igual para escalas diferentes")
    void shouldHaveSameHashCodeWhenScaleIsDifferent() {

        Money first = Money.of("1000.0");
        Money second = Money.of("1000.00");

        assertEquals(
                first.hashCode(),
                second.hashCode());

    }

    @Test
    @DisplayName("HashCode deve ser dioferente para valores diferentes")
    void shouldHaveDifferentHashCodeWhenValuesAreDifferent() {

        Money first = Money.of("1000.00");
        Money second = Money.of("999.99");

        assertNotEquals(
                first.hashCode(),
                second.hashCode());

    }

    @Test
    @DisplayName("Garatir que o Comparable continue consistente com o restante da classe")
    void shouldBeComparable() {

        List<Money> values = List.of(
                Money.of("1500"),
                Money.of("500"),
                Money.of("1000"));

        List<Money> sorted = values.stream()
                .sorted()
                .toList();

        assertEquals(Money.of("500"), sorted.get(0));
        assertEquals(Money.of("1000"), sorted.get(1));
        assertEquals(Money.of("1500"), sorted.get(2));

    }

}