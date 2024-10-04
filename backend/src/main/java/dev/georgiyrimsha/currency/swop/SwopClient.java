package dev.georgiyrimsha.currency.swop;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "https://swop.cx/rest", name = "swop")
public interface SwopClient {

    @GetMapping("rates/{baseCurrency}/{quoteCurrency}")
    GetExchangeRateResponse getExchangeRate(@PathVariable("baseCurrency") String baseCurrency,
                                            @PathVariable("quoteCurrency") String quoteCurrency);

    @GetMapping("currencies")
    List<GetCurrencyResponse> getAllCurrencies();
}
