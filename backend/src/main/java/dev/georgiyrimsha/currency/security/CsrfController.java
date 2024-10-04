package dev.georgiyrimsha.currency.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("csrf")
public class CsrfController {

    @GetMapping
    public CsrfToken csrf(CsrfToken csrfToken) {
        log.info("Request to get csrf token [{}: {}]", csrfToken.getHeaderName(), csrfToken.getToken());
        return csrfToken;
    }
}
