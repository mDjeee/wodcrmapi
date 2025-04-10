package com.example.wodcrmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.example.wodcrmapi")
@EnableJpaAuditing
public class WodcrmapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WodcrmapiApplication.class, args);
	}

}
