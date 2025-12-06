package com.factory.tycoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// @SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TycoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TycoonApplication.class, args);
	}

}
