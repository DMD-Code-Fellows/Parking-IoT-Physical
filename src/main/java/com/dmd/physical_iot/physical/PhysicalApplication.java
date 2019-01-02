package com.dmd.physical_iot.physical;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PhysicalApplication {

	public static void main(String args[]) throws InterruptedException {

		System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

		/**
		 * code to show change of server database, upon bootrun of pi
		 * Comment in below and comment out COMPLETELY line: 45 through 124
		 */
//		LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
//		paramsMap.add("parkingLotName", "Parking Lot One");
//		paramsMap.add("parkingSpaceName", "R1-1");
//		paramsMap.add("parkingSpaceEvent", "OCCUPY");
//
//		WebClient.RequestHeadersSpec requestSpec = WebClient
//				.create("http://172.16.2.228:8080")
//				.put()
//				.uri("/space-map/update")
//				.body(BodyInserters.fromMultipartData(paramsMap));
//
//		String responseSpec = requestSpec.retrieve()
//				.bodyToMono(String.class)
//				.block();
//		System.out.println(responseSpec);


		// create GPIO controller
		final GpioController gpio = GpioFactory.getInstance();

		// create GPIO listener
		GpioPinListenerDigital listener  = new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				// display pin state on console
				System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin().getName() + " = " + event.getState());

				LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
				paramsMap.add("parkingLotName", "Parking Lot One");
				paramsMap.add("parkingSpaceName", event.getPin().getName());
				paramsMap.add("parkingSpaceEvent", "OCCUPY");

				/**
				 * code where I was playing around with flux instead of mono
				 */
//				WebClient requestSpec = WebClient
//						.create("http://172.16.2.228:8080");
//
//
//				Flux<String> responseSpec = requestSpec.put()
//						.uri("/space-map/update")
//						.body(BodyInserters.fromMultipartData(paramsMap))
//						.retrieve()
//						.bodyToFlux(String.class)
//						.flatMap(e -> Mono.justOrEmpty(e));
//
//
//				responseSpec.subscribe(str -> System.out.println("Received: {}" + str));

				/**
				 * another flux webclient configuration
				 */
//				WebClient.RequestHeadersSpec requestSpec = WebClient
//						.create("http://172.16.2:8080")
//						.put()
//						.uri("/space-map/update")
//						.body(BodyInserters.fromMultipartData(paramsMap));
//
//				Flux<String> responseSpec = requestSpec.retrieve()
//						.bodyToFlux(String.class)
//						.flatMap(entry -> Flux.just(entry));
//
//				System.out.println(responseSpec);
//			}

				/**
				 * code from tuesday added in with block duration to see if this would provide enought client to respond
				 */
				WebClient.RequestHeadersSpec requestSpec = WebClient
						.create("http://172.16.2.228:8080")
						.put()
						.uri("/space-map/update")
						.body(BodyInserters.fromMultipartData(paramsMap));

			String responseSpec = requestSpec.retrieve()
						.bodyToMono(String.class)
						.block(Duration.ofSeconds(5));
				System.out.println(responseSpec);
			}


		};

		// provision gpio input pins with its internal pull down resistor enabled
		GpioPinDigitalInput[] pins = {
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "R1-1", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "R1-2", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "R1-3", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "R1-4", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "R1-5", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "R1-6", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, "R2-1", PinPullResistance.PULL_DOWN),
				gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "R2-2", PinPullResistance.PULL_DOWN),
		};

		 //create and register gpio pin listener
		gpio.addListener(listener, pins);

		System.out.println(" ... complete the GPIO circuit and see the listener feedback here in the console.");

		// keep program running until user aborts (CTRL-C)
//		while(true) {
//			Thread.sleep(500);
//		}

		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
		// gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
	}
	static {
		System.setProperty("pi4j.linking", "dynamic");
	}
}
