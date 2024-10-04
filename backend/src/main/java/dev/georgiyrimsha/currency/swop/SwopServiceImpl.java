package dev.georgiyrimsha.currency.swop;

import dev.georgiyrimsha.currency.exception.CurrencyListNotAvailableException;
import dev.georgiyrimsha.currency.exception.RateNotAvailableException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SwopServiceImpl implements SwopService {

    private final SwopClient swopClient;

    @Cacheable(value = "rates",
            key = "T(dev.georgiyrimsha.currency.util.CurrencyUtils).currenciesPair(#p0, #p1)"
    )
    public BigDecimal getExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        if (sourceCurrencyCode.equals(targetCurrencyCode)) {
            return BigDecimal.ONE;
        }

        GetExchangeRateResponse response;
        try {
            response = swopClient.getExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        } catch (FeignException ex) {
            throw new RateNotAvailableException(ex.getMessage());
        }

        return BigDecimal.valueOf(response.quote());
    }

    @Cacheable(value = "currencies")
    public List<String> getAllAvailableCurrencies() {
        try {
            return swopClient.getAllCurrencies().stream()
                    .filter(GetCurrencyResponse::active)
                    .map(GetCurrencyResponse::code)
                    .toList();
        } catch (FeignException ex) {
            throw new CurrencyListNotAvailableException(ex.getMessage());
        }
    }

}
