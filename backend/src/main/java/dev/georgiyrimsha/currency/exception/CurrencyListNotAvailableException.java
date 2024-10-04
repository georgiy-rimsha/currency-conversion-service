package dev.georgiyrimsha.currency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FAILED_DEPENDENCY)
public class CurrencyListNotAvailableException extends RuntimeException {
    public CurrencyListNotAvailableException(String message) {
        super(message);
    }
}
