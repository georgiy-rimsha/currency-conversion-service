package dev.georgiyrimsha.currency.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class CurrencyUtils {

    private static final String CURRENCY_PAIR_SPLITTER = "_";
    private static final Pattern CODE = Pattern.compile("[A-Z]{3}", Pattern.CASE_INSENSITIVE);

    public static String currenciesPair(final String sourceCurrencyCode, final String targetCurrencyCode) {
        Objects.requireNonNull(sourceCurrencyCode, "sourceCurrencyCode must not be null");
        Objects.requireNonNull(targetCurrencyCode, "targetCurrencyCode must not be null");
        return sourceCurrencyCode + CURRENCY_PAIR_SPLITTER + targetCurrencyCode;
    }

    public static void validateCurrencyCode(final String currencyCode, List<String> availableCurrencyCodes) {
        Objects.requireNonNull(currencyCode, "Currency code must not be null");
        if (!CODE.matcher(currencyCode).matches()) {
            throw new IllegalArgumentException("Currency string code must be three letters");
        }
        if (availableCurrencyCodes.stream().noneMatch(code -> code.equalsIgnoreCase(currencyCode))) {
            throw new IllegalArgumentException("Currency not available");
        }
    }
}
