package br.com.azk.pricesentinel.domain.enums;

public enum Store {

    AMAZON("Amazon"),
    KABUM("KaBuM!"),
    PICHAU("Pichau"),
    TERABYTE("Terabyte"),
    MERCADO_LIVRE("Mercado Livre"),
    GLACON("Glacon");

    private final String description;

    Store(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
