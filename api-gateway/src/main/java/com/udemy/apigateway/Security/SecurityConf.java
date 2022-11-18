package com.udemy.apigateway.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConf {

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity){
        return httpSecurity.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/product",
                        "/api/item",
                        "/api/user/userRepo",
                        "/api/item/detail/{id}/quantity/{quantity}",
                        "/api/product/detail/{id}").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/user/userRepo/{id}").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/product/**", "/api/item/**", "/api/user/userRepo/**").hasRole("ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf()
                .disable()
                .build();
    }
}
