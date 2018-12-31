package com.dmd.physical_iot.physical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@SpringBootApplication
public class PhysicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysicalApplication.class, args);
		System.out.println("Hello Raspberry PI!");
		System.out.println("http://localhost:8080");

		LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
		paramsMap.add("parkingLotName", "Parking Lot One");
		paramsMap.add("parkingSpaceName", "R1-1");
		paramsMap.add("parkingSpaceEvent", "OCCUPY");

		WebClient.RequestHeadersSpec requestSpec = WebClient
				.create()
				.put()
				.uri(URI.create("http://172.16.15.75:8080/space-map/update"))
				.body(BodyInserters.fromMultipartData(paramsMap));

				String responseSpec = requestSpec.retrieve()
				.bodyToMono(String.class)
				.block();
				System.out.println(responseSpec);
	}

}
