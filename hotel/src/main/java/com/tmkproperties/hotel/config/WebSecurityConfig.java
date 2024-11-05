package com.tmkproperties.hotel.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,"api/v1/hotels").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/hotels/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/rooms").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/rooms/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
        .oauth2ResourceServer(rsc -> rsc.jwt(jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.build();
    }
}
