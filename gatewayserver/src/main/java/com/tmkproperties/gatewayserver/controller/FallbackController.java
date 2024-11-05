package com.tmkproperties.gatewayserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class FallbackController {

    private static final Logger logger = LoggerFactory.getLogger(FallbackController.class);
    private static final String ERROR_MESSAGE = "An error occurred. Please try again later or contact our support team.";

    @GetMapping("/contact-support")
    public Mono<ResponseEntity<String>> contactSupport() {
        logger.error("Fallback triggered: Service unavailable.");

        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ERROR_MESSAGE));
    }
}
