package dev.georgiyrimsha.currency.web;

import dev.georgiyrimsha.currency.service.ConversionResult;
import dev.georgiyrimsha.currency.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@Slf4j
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    @GetMapping("conversions/{sourceCurrency}/{targetCurrency}")
    public ConvertResponse convert(
            @PathVariable("sourceCurrency") String sourceCurrency,
            @PathVariable("targetCurrency") String targetCurrency,
            @RequestParam("amount") BigDecimal amount) {

        log.info("Request to convert [{}] of [{}] to [{}]", amount, sourceCurrency, targetCurrency);
        return makeConvertResponse(currencyConversionService.convertCurrency(sourceCurrency, targetCurrency, amount));
    }

    @GetMapping("currencies")
    public List<String> getAvailableCurrencies() {
        log.info("Request to get available currencies");
        return currencyConversionService.getAllAvailableCurrencies();
    }

    @PostMapping("post")
    public void post() {
        log.info("Request to modify server state");
    }

    private ConvertResponse makeConvertResponse(ConversionResult conversionResult) {
        return new ConvertResponse(conversionResult.convertedAmount(), conversionResult.rate());
    }

}
