package dev.georgiyrimsha.currency.swop;

import java.time.LocalDate;

public record GetExchangeRateResponse(
        String baseCurrency,
        String quoteCurrency,
        double quote,
        LocalDate date
) {}

