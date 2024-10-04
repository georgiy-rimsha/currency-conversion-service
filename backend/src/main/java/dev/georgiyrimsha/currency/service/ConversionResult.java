package dev.georgiyrimsha.currency.service;

import java.math.BigDecimal;

public record ConversionResult(BigDecimal convertedAmount, BigDecimal rate) {
}
