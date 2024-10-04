package dev.georgiyrimsha.currency.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RateNotAvailableException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ResponseEntity<Map<String, String>> handleRateNotAvailableException(RateNotAvailableException ex) {
        log.info("Rate not available: {}", ex.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Rate Not Available");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(errorResponse);
    }

    @ExceptionHandler(CurrencyListNotAvailableException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ResponseEntity<Map<String, String>> handleCurrencyListNotAvailableException(CurrencyListNotAvailableException ex) {
        log.info("Currency list not available: {}", ex.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Currency List Not Available");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(errorResponse);
    }

    @ExceptionHandler(RequestValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleRequestValidationException(RequestValidationException ex) {
        log.info("Bad request: {}", ex.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Request Not Valid");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
