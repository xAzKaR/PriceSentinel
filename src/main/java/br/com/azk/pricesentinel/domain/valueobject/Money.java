package br.com.azk.pricesentinel.domain.valueobject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public record Money(BigDecimal value) {

    public Money {
        Objects.requireNonNull(value, "Money cannot be null");
    }

    public static Money of(String value) {
        return new Money(new BigDecimal((value)));
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public boolean isLessThanOrEqual(Money other) {
        return value.compareTo(other.value) <= 0;
    }

    public boolean isGreaterThan(Money other) {
        return value.compareTo(other.value) > 0;
    }

    public Money subtract(Money other) {
        return new Money(value.subtract(other.value));
    }

    public Money add(Money other) {
        return new Money(value.add(other.value));
    }

    public Money abs() {
        return new Money(value.abs());
    }

    @Override
    public String toString() {
        NumberFormat formatter =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return formatter.format(value);
    }

}
