package com.dmd.physical_iot.physical;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;



	@SpringBootApplication
	public class PhysicalApplication {
		public static void main(String[] args) {

			SpringApplication.run(PhysicalApplication.class, args);
			System.out.println("<--Pi4J--> GPIO Trigger Example ... started.");
			// create gpio controller
			final GpioController gpio = GpioFactory.getInstance();
			// provision gpio pin #02 as an input pin with its internal pull down resistor enabled
			final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "R1-1",
					PinPullResistance.PULL_DOWN);
			// create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
			// invocation on the user defined 'Callable' class instance

			myButton.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {
					Boolean status =  myButton.getState().isLow();
					String sendStatus = (status) ? "VACATE" : "OCCUPY";
					System.out.println(" --> GPIO TRIGGER CALLBACK heck RECEIVED" + myButton.getState() + status + sendStatus);
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", "R1-1");
					paramsMap.add("parkingSpaceEvent", sendStatus);
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://172.16.2.228:8080")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + myButton.getState() + status + sendStatus);

					return null;
				}
			}));





		}
		static {
			System.setProperty("pi4j.linking", "dynamic");
		}
	}
