package com.unit.mockito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnitAnDintegrationTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitAnDintegrationTestingApplication.class, args);
		System.out.println("\nUnit Testing,\nIntegration Testing,\nMockito,,");
	}

}
