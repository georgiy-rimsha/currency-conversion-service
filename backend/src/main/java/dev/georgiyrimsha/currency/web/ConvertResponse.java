package dev.georgiyrimsha.currency.web;

import java.math.BigDecimal;

public record ConvertResponse(BigDecimal convertedAmount, BigDecimal conversionRate) {
}
