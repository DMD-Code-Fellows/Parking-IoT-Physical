//package com.dmd.physical_iot.physical;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//@Configuration
//public class PhysicalRouter {
//
//    @Bean
//    public RouterFunction<ServerResponse> route(PhysicalHandler physicalHandler){
//
//        return RouterFunctions
//                .route(RequestPredicates.PUT("/space-map/update").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), physicalHandler::paramsMap);
//    }
//}
