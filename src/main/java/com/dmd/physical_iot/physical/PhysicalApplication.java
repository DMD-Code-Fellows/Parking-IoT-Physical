package com.dmd.physical_iot.physical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhysicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysicalApplication.class, args);
		System.out.println("Hello Raspberry PI!");
		System.out.println("http://localhost:8080");

	}

}
