package dev.georgiyrimsha.currency.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyUtilsTest {

    private List<String> availableCurrencies;

    {
        availableCurrencies = Arrays.asList("USD", "EUR", "GBP", "BTC");
    }

    @Test
    void validateCurrenciesSuccess() {
        CurrencyUtils.validateCurrencyCode("USD", availableCurrencies);
        CurrencyUtils.validateCurrencyCode("EUR", availableCurrencies);
        CurrencyUtils.validateCurrencyCode("GBP", availableCurrencies);
        CurrencyUtils.validateCurrencyCode("BTC", availableCurrencies);
    }

    @Test
    void validateCurrenciesFailed() {
        assertThrows(NullPointerException.class, () -> CurrencyUtils.validateCurrencyCode(null, availableCurrencies));
        assertThrows(IllegalArgumentException.class, () -> CurrencyUtils.validateCurrencyCode("", availableCurrencies));
        assertThrows(IllegalArgumentException.class, () -> CurrencyUtils.validateCurrencyCode("123", availableCurrencies));
        assertThrows(IllegalArgumentException.class, () -> CurrencyUtils.validateCurrencyCode("QAR", availableCurrencies));
    }
}