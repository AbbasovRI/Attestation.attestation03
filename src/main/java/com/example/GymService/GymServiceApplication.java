package com.example.GymService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gym Service API", version = "1.0", description = "Gym Training Booking Service"))
public class GymServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymServiceApplication.class, args);
	}
}