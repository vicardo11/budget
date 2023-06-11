package it.sosinski.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator routes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/expenses/**")
                        .or()
                        .path("/income/**")
                        .or()
                        .path("/account-balance/**")
                        .uri("http://localhost:8200"))
                .route(p -> p
                        .path("/currency-exchange/**")
                        .uri("http://localhost:8300"))
                .build();
    }

}
