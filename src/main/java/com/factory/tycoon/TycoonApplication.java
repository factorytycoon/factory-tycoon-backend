package com.factory.tycoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.factory.tycoon.auth.JwtProps;

@EnableConfigurationProperties(JwtProps.class)
@SpringBootApplication
@EnableJpaAuditing

public class TycoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TycoonApplication.class, args);
	}

}
