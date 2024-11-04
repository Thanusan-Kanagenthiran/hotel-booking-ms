package com.tmkproperties.hotel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Hotel Service - TMK Properties - Hotel Management System",
				description = """
						This is a Hotel Management System for TMK Properties.
						""",
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
public class HotelApplication {
	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

}
