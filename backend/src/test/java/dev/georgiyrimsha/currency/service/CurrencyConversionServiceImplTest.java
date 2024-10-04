package dev.georgiyrimsha.currency.service;

import dev.georgiyrimsha.currency.exception.RateNotAvailableException;
import dev.georgiyrimsha.currency.exception.RequestValidationException;
import dev.georgiyrimsha.currency.swop.SwopService;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {

    @Mock
    private SwopService swopService;

    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    public void setup() {
        List<String> availableCurrencies = Arrays.asList("USD", "EUR", "GBP");
        when(swopService.getAllAvailableCurrencies()).thenReturn(availableCurrencies);

        currencyConversionService = new CurrencyConversionServiceImpl(swopService, new SimpleMeterRegistry());
    }

    @Test
    public void canConvertCurrency() {
        // Given
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal exchangeRate = BigDecimal.valueOf(0.85);

        // When
        when(swopService.getExchangeRate(sourceCurrency, targetCurrency)).thenReturn(exchangeRate);

        ConversionResult result = currencyConversionService.convertCurrency(sourceCurrency, targetCurrency, amount);

        // Then
        assertNotNull(result);
        assertEquals(exchangeRate.multiply(amount), result.convertedAmount());
        assertEquals(exchangeRate, result.rate());

        verify(swopService, times(1)).getAllAvailableCurrencies();
        verify(swopService, times(1)).getExchangeRate(sourceCurrency, targetCurrency);
    }

    @Test
    public void cannotConvertCurrencyWhenSourceCurrencyIsInvalid() {
        // Given
        String sourceCurrency = "INVALID";
        String targetCurrency = "EUR";
        BigDecimal amount = BigDecimal.valueOf(100);

        // When
        // Then
        assertThrows(RequestValidationException.class, () -> {
            currencyConversionService.convertCurrency(sourceCurrency, targetCurrency, amount);
        });

        verify(swopService, times(1)).getAllAvailableCurrencies();
        verify(swopService, never()).getExchangeRate(anyString(), anyString());
    }

    @Test
    public void cannotConvertCurrencyWhenTargetCurrencyIsInvalid() {
        // Given
        String sourceCurrency = "USD";
        String targetCurrency = "INVALID";
        BigDecimal amount = BigDecimal.valueOf(100);

        // When

        // Then
        assertThrows(RequestValidationException.class, () -> {
            currencyConversionService.convertCurrency(sourceCurrency, targetCurrency, amount);
        });

        verify(swopService, times(1)).getAllAvailableCurrencies();
        verify(swopService, never()).getExchangeRate(anyString(), anyString());
    }

    @Test
    public void cannotConvertCurrencyWhenExchangeRateNotFound() {
        // Given
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        BigDecimal amount = BigDecimal.valueOf(100);

        // When
        when(swopService.getExchangeRate(sourceCurrency, targetCurrency)).thenThrow(new RateNotAvailableException("Exchange conversionRate not found"));

        // Then
        assertThrows(RuntimeException.class, () -> {
            currencyConversionService.convertCurrency(sourceCurrency, targetCurrency, amount);
        });

        verify(swopService, times(1)).getAllAvailableCurrencies();
        verify(swopService, times(1)).getExchangeRate(sourceCurrency, targetCurrency);
    }

}