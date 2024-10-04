package dev.georgiyrimsha.currency.journey;

import dev.georgiyrimsha.currency.web.ConvertResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class CurrencyConversionIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canConvertCurrency() {
        // Given
        String sourceCurrency = "EUR";
        String targetCurrency = "USD";
        BigDecimal amount = new BigDecimal("100");

        // When
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/conversions/{sourceCurrency}/{targetCurrency}")
                        .queryParam("amount", amount)
                        .build(sourceCurrency, targetCurrency))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // Then
                .expectStatus().isOk()
                .expectBody(ConvertResponse.class)
                .value(response -> {
                    assertThat(response.convertedAmount()).isNotNull();
                    assertThat(response.conversionRate()).isNotNull();
                });
    }

}