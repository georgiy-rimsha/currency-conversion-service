package dev.georgiyrimsha.currency.swop;

public record GetCurrencyResponse(String code,
                                  String numericCode,
                                  int decimalDigits,
                                  String name,
                                  boolean active) {
}
