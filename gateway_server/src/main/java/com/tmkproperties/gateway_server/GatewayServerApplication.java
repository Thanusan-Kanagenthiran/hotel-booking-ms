package com.tmkproperties.gateway_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()

				.route("gateway_server_route", r -> r.path("/gateway_server/**")
						.filters(f -> f.rewritePath("/gateway_server/(?<remaining>.*)", "/${remaining}")
								.circuitBreaker(config -> config
										.setName("gatewayServerCircuitBreaker")
										.setFallbackUri("forward:/contact-support")
										.setRouteId("gateway_server_circuitbreaker")))
						.uri("lb://GATEWAY_SERVER"))

				.route("hotel_route", r -> r.path("/hotel/**")
						.filters(f -> f.rewritePath("/hotel/(?<remaining>.*)", "/${remaining}")
								.circuitBreaker(config -> config
										.setName("hotelCircuitBreaker")
										.setFallbackUri("forward:/contact-support")
										.setRouteId("hotel_circuitbreaker")))
						.uri("lb://HOTEL"))

				.route("booking_route", r -> r.path("/booking/**")
						.filters(f -> f.rewritePath("/booking/(?<remaining>.*)", "/${remaining}")
								.circuitBreaker(config -> config
										.setName("bookingCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")
										.setRouteId("booking_circuitbreaker")))
						.uri("lb://BOOKING"))
				.build();
	}

}
