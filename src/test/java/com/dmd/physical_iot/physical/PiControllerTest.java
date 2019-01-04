package com.dmd.physical_iot.physical;
import org.junit.Test;

import static org.junit.Assert.*;

public class PiControllerTest {

    @Test
    public void helloWorldTest() {
        String expected = "hello world!";

        String homeRun = PiController.home();

        assertEquals(expected, homeRun);


    }

}