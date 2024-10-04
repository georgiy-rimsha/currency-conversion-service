package dev.georgiyrimsha.currency.service;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyConversionService {

    ConversionResult convertCurrency(String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amount);

    List<String> getAllAvailableCurrencies();

}
