package com.tmkproperties.gateway_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator TMKPropertiesHotelManagementRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/tmk-properties/hotel/**")
						.filters(f -> f
								.rewritePath("/tmk-properties/hotel/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("hotelServiceCircuitBreaker").setFallbackUri("forward:/contact-support"))
								.addRequestHeader("X-User-ID", "12345") // Example static userId, you may replace this with dynamic values
								.addRequestHeader("X-USER-ROLE", "admin") // Example static email
								.addResponseHeader("X-Requested-Time", LocalDateTime.now().toString()))
						.uri("lb://HOTEL"))
				.route(p -> p
						.path("/tmk-properties/booking/**")
						.filters(f -> f
								.rewritePath("/tmk-properties/booking/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("bookingServiceCircuitBreaker").setFallbackUri("forward:/contact-support"))
								.addRequestHeader("userId", "12345") // Example static userId
								.addRequestHeader("email", "user@example.com") // Example static email
								.addResponseHeader("X-Requested-Time", LocalDateTime.now().toString()))
						.uri("lb://BOOKING"))
				.build();
	}



}
