package dev.georgiyrimsha.currency.service;

import dev.georgiyrimsha.currency.exception.RequestValidationException;
import dev.georgiyrimsha.currency.swop.SwopService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static dev.georgiyrimsha.currency.util.CurrencyUtils.validateCurrencyCode;

@Slf4j
@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final SwopService swopService;
    private final MeterRegistry meterRegistry;

    private final Timer conversionTimer;
    private final Counter.Builder conversionCounter;

    public CurrencyConversionServiceImpl(SwopService swopService, MeterRegistry meterRegistry) {
        this.swopService = swopService;
        this.meterRegistry = meterRegistry;

        this.conversionTimer = meterRegistry.timer("currency.conversion.time");
        this.conversionCounter = Counter.builder("currency.conversion.count");
    }

    /**
     * Converts the specified amount from the source currency to the target currency.
     * This method also increments a counter to track conversion requests and records
     * the time taken to perform the conversion using a timer.
     *
     * @param sourceCurrencyCode    the ISO code of the source currency (e.g., "USD")
     * @param targetCurrencyCode    the ISO code of the target currency (e.g., "EUR")
     * @param amount                the amount of money to be converted
     * @return                      the result of the currency conversion, including the converted amount and rate
     */
    @Override
    public ConversionResult convertCurrency(String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        conversionCounter
                .tags("source_currency", sourceCurrencyCode, "target_currency", targetCurrencyCode)
                .register(meterRegistry)
                .increment();

        return conversionTimer.record(() -> performCurrencyConversion(sourceCurrencyCode, targetCurrencyCode, amount));
    }

    private ConversionResult performCurrencyConversion(String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        List<String> availableCurrencies = swopService.getAllAvailableCurrencies();

        try {
            validateCurrencyCode(sourceCurrencyCode, availableCurrencies);
            validateCurrencyCode(targetCurrencyCode, availableCurrencies);
        } catch (RuntimeException ex) {
            throw new RequestValidationException(ex.getMessage());
        }

        BigDecimal conversionRate = swopService.getExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        return new ConversionResult(conversionRate.multiply(amount), conversionRate);
    }

    @Override
    public List<String> getAllAvailableCurrencies() {
        return swopService.getAllAvailableCurrencies();
    }

}
