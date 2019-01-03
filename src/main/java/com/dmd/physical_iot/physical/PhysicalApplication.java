package com.dmd.physical_iot.physical;

import com.dmd.iot.parking_iot.common.ParkingSpaceEvents;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.concurrent.Callable;
import static com.dmd.iot.parking_iot.common.ParkingSpaceEvents.OCCUPY;
import static com.dmd.iot.parking_iot.common.ParkingSpaceEvents.VACATE;

/**
 * A Spring Boot parking Internet of Things (IoT) physical-device application.
 *
 * An Internet-of-Things (IoT) application, that automates parking space availability in a parking lot, so that drivers can
 * quickly locate the nearest available space, in order to save time and fuel, and that also helps parking lot owners and attendants,
 * monitor and manage their lots.
 */

@SpringBootApplication
	public class PhysicalApplication {

	/**
	 * Java application entry point.
	 * @param args An array of application input arguments. Not used.
	 */
		public static void main(String[] args) {

			/**
			 * static helper that used to run spring application using default setting
			 */
			SpringApplication.run(PhysicalApplication.class, args);
			System.out.println("<--DMD--> GPIO Trigger ... started.");

			/**
			 * @class GpioFactory  provies a static method to create a new GPIO controller instance.
			 *
			 */
			final GpioController gpio = GpioFactory.getInstance();
			// provision gpio pin #02 as an input pin with its internal pull down resistor enabled

			final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "R2-6",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "R1-1",
					PinPullResistance.PULL_DOWN);

			// create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
			// invocation on the user defined 'Callable' class instance

			myButton1.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					Boolean status =  myButton1.getState().isLow();
					String sendStatus = (status) ? "VACATE" : "OCCUPY";
					String spotName = myButton1.getName();
					System.out.println(" --> GPIO TRIGGER CALLBACK heck RECEIVED" + myButton1.getState() + status + sendStatus);
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", "R1-6");
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + myButton1.getState() + status + sendStatus);

					return null;
				}
			}));

			myButton2.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {
					Boolean status =  myButton2.getState().isLow();
					String sendStatus = (status) ? "VACATE" : "OCCUPY";
					String spotName = myButton2.getName();
					System.out.println(" --> GPIO TRIGGER CALLBACK heck RECEIVED" + myButton1.getState() + status + sendStatus);
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus);
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://172.16.13.140:8080")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + myButton1.getState() + status + sendStatus);

					return null;
				}
			}));




		}
		static {
			System.setProperty("pi4j.linking", "dynamic");
		}
	}
