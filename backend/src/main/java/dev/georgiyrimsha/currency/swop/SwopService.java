package dev.georgiyrimsha.currency.swop;

import java.math.BigDecimal;
import java.util.List;

public interface SwopService {

    BigDecimal getExchangeRate(String sourceCurrencyCode, String targetCurrencyCode);

    List<String> getAllAvailableCurrencies();
}
