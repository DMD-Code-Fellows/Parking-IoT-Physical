package com.dmd.physical_iot.physical;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PhysicalWebClient {
    private WebClient client = WebClient.create("http://172.16.2.228:8080");

    private Mono<ClientResponse> result = client.put()
            .uri("/space-map/update")
            .accept(MediaType.TEXT_PLAIN)
            .exchange();

    public String getResult(){
        return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
    }
}