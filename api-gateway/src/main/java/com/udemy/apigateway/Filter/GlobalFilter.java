package com.udemy.apigateway.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter {

    private final Logger log = LoggerFactory.getLogger(GlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("EJECUTO EL FILTRO PRE");

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            log.info("EJECUTO FILTRO POST");

            exchange.getResponse().getCookies().add("COLOR", ResponseCookie.from("COLOR", "ROJO").build());
            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }
}
