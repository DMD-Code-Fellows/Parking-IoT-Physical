package com.dmd.physical_iot.physical;

import com.dmd.iot.parking_iot.common.ParkingSpaceEvents;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

import static com.dmd.iot.parking_iot.common.ParkingSpaceEvents.OCCUPY;
import static com.dmd.iot.parking_iot.common.ParkingSpaceEvents.VACATE;

@Component
public class PhysicalHandler {

    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "R2-6",
            PinPullResistance.PULL_DOWN);
    final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "R1-1",
            PinPullResistance.PULL_DOWN);

    ParkingSpaceEvents currentState(GpioPinDigitalInput button){
        Boolean status =  button.getState().isLow();
        ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;
        return sendStatus;

    myButton1.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
        @Override
        public Void call() throws Exception {

            String status = currentState(myButton1).toString();

            System.out.println(" --> GPIO TRIGGER CALLBACK heck RECEIVED" + myButton1.getState() + status + sendStatus);

            LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
            paramsMap.add("parkingLotName", "Parking Lot One");
            paramsMap.add("parkingSpaceName", myButton1.getName());
            paramsMap.add("parkingSpaceEvent", status);

            return paramsMap;

        }

            return null;
        }
    }));

    Mono<ServerResponse> hello(ServerRequest request) {
        return request
                .body(BodyInserters.fromMultipartData(paramsMap));



}
