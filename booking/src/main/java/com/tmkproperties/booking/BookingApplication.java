package com.tmkproperties.booking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Booking microservice REST API Documentation",
                description = "This is a REST API documentation for Booking microservice of " +
                        "TMK Properties - Hotel Management System",
                version = "v1",
                contact = @Contact(
                        name = "Thanusan Kanagenthiran",
                        email = "thanushmk6@gmail.com",
                        url = "https://github.com/Thanusan-Kanagenthiran"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://github.com/Thanusan-Kanagenthiran/hotel-booking-ms/blob/main/LICENSE"
                )
        )
)
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

}
