package com.dmd.physical_iot.physical;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PiController {

    @RequestMapping("/")
    public static String home(){
        return "hello world!";
    }
}
