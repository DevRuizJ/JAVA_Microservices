package com.udemy.apigateway.FilterFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class DemoGatewayFilterFactory extends AbstractGatewayFilterFactory<DemoGatewayFilterFactory.Configuration> {

    public DemoGatewayFilterFactory(){
        super(Configuration.class);
    }

    private final Logger log = LoggerFactory.getLogger(DemoGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(DemoGatewayFilterFactory.Configuration config) {
        return ((exchange, chain) -> {

            log.info("STEP 2 - FILTER FACTORY PRE: " + config.message);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                Optional.ofNullable(config.coockieValor).ifPresent(coockie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.coockieName, coockie).build());
                });

                log.info("STEP 2 - FILTER FACTORY POST: " + config.message);
            }));
        });
    }

    public static class Configuration{
        private String message, coockieValor, coockieName;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCoockieValor() {
            return coockieValor;
        }

        public void setCoockieValor(String coockieValor) {
            this.coockieValor = coockieValor;
        }

        public String getCoockieName() {
            return coockieName;
        }

        public void setCoockieName(String coockieName) {
            this.coockieName = coockieName;
        }
    }
}
