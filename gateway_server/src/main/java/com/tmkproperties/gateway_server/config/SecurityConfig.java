package com.tmkproperties.gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/hotel/api/v1/hotels", "/tmk-properties/hotel/api/v1/hotels/{id}").permitAll()
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/hotel/api/v1/hotels/host/**").hasRole("HOST")
                        .pathMatchers(HttpMethod.POST, "/tmk-properties/hotel/api/v1/hotels/host/**").hasRole("HOST")
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/hotel/api/v1/rooms", "/tmk-properties/hotel/api/v1/rooms/{id}").permitAll()
                        .pathMatchers(HttpMethod.POST, "/tmk-properties/hotel/api/v1/rooms/host/**").hasRole("HOST")
                        .pathMatchers(HttpMethod.POST, "/tmk-properties/booking/api/v1/bookings/host/{id}/**").hasRole("HOST")
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/booking/api/v1/bookings/host/{id}").hasRole("HOST")
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/booking/api/v1/bookings/host").hasRole("HOST")
                        .pathMatchers(HttpMethod.POST, "/tmk-properties/booking/api/v1/bookings/user/{id}/**").hasRole("USER")
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/booking/api/v1/bookings/user/{id}").hasRole("USER")
                        .pathMatchers(HttpMethod.PUT, "/tmk-properties/booking/api/v1/bookings/user/{id}").hasRole("USER")
                        .pathMatchers(HttpMethod.POST, "/tmk-properties/booking/api/v1/bookings/user").hasRole("USER")
                        .pathMatchers(HttpMethod.GET, "/tmk-properties/booking/api/v1/bookings/user").hasRole("USER")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return serverHttpSecurity.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
