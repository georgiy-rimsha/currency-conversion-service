package dev.georgiyrimsha.currency.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    @Value("${csp.api-url}")
    private String apiUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .headers(headers ->
                        headers
                                .contentSecurityPolicy(csp ->
                                        csp.policyDirectives(
                                                "default-src 'self'; " +
                                                        "script-src 'self'; " +
                                                        "connect-src 'self' " + apiUrl + ";"
                                        )
                                )
                );

        return http.build();
    }
}
