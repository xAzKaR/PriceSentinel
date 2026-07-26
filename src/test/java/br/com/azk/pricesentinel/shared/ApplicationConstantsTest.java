package br.com.azk.pricesentinel.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationConstantsTest {

    @Test
    @DisplayName("Validando application name")
    void shoudReturnTrueConstantName() {
        Assertions.assertEquals("PriceSentinel", ApplicationConstants.APPLICATION_NAME);
    }

    @Test
    @DisplayName("Validando application version")
    void shoudReturnTrueConstantsVersion() {
        Assertions.assertEquals("0.0.1", ApplicationConstants.VERSION);
    }


}