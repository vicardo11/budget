package it.sosinski.apigateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGatewayFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return exchange.getPrincipal().flatMap(principal -> {
            String email = getEmail(principal);

            return chain.filter(
                    exchange.mutate().request(
                                    exchange.getRequest().mutate()
                                            .header("email", email)
                                            .build())
                            .build());
        });
    }

    private String getEmail(Principal principal) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) principal;
        Jwt token = jwtAuthenticationToken.getToken();
        Map<String, Object> claims = token.getClaims();
        return claims.get("email").toString();
    }
}
