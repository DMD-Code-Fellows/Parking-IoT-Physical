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

			/**
			 * Creates a decorator interface to describe digital digital input pin using GPIO controller interface. Provisions input pin with pin and name parameter with internal pull down resistor enabled.
			 * @param pin pin
			 * @param String name
			 */
			final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "R2-6",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "R1-1",
					PinPullResistance.PULL_DOWN);

			/**
			 * Creates a gpio callback trigger internally when the state of the decorator interface changes.
			 * @param invokable GpioCallbackTrigger method
			 * @param Pinstate state
			 * @param Callable<void> callback
			 */
			myButton1.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton1.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton1.getName();
					System.out.println(" --> GPIO TRIGGER CALLBACK heck RECEIVED" + myButton1.getState() + status + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + myButton1.getState() + status + sendStatus);

					return null;
				}
			}));

			/**
			 * Creates a gpio callback trigger internally when the state of the decorator interface changes.
			 * @param invokable GpioCallbackTrigger method
			 * @param Pinstate state
			 * @param Callable<void> callback
			 */
			myButton2.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton2.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton2.getName();
					System.out.println(" --> GPIO TRIGGER CALLBACK heck RECEIVED" + myButton1.getState() + status + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + myButton1.getState() + status + sendStatus);

					return null;
				}
			}));


			/**
			 * static block for dynamic linking use of wiring pi
			 */
		}
		static {
			System.setProperty("pi4j.linking", "dynamic");
		}
	}
