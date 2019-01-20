package com.dmd.physical_iot.physical;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PiController {

    // Why do you have this controller? I don't think you need to create a server listening on the Pi at all.
    @RequestMapping("/")
    public static String home(){
        return "hello world!";
    }
}
