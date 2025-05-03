package com.example.GymService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Gym Service API",
				version = "1.0",
				description = "API для управления спортсменами, тренерами и тренировками в спортзале.",
				contact = @Contact(name = "Support", email = "supportrustam@example.com"),
				license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")
		)
)
@SpringBootApplication
public class GymServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymServiceApplication.class, args);
	}
}