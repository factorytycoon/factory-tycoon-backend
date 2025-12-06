package com.factory.tycoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TycoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TycoonApplication.class, args);
	}

}
