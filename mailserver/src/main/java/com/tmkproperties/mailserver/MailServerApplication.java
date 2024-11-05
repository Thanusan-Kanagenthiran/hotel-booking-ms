package com.tmkproperties.mailserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Mail Server API",
				description = "This is a Mail Server API for " +
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
@EnableFeignClients
public class MailServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailServerApplication.class, args);
	}

}
