package com.udemy.apigateway.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

    private final Logger log = LoggerFactory.getLogger(GlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("STEP 1 - EL FILTRO PRE");

        exchange.getRequest().mutate().headers(h -> h.add("Token", "123456"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            log.info("STEP 2 - FILTRO POST");

            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("Token")).ifPresent(valor -> {
                exchange.getResponse().getHeaders().add("Token", valor);
            });

            exchange.getResponse().getCookies().add("COLOR", ResponseCookie.from("COLOR", "VERDE").build());
            //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
