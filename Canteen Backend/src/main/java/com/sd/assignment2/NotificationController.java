package com.sd.assignment2;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notification")
    @SendTo("/sub")
    public String newMessage(String string) throws InterruptedException {
        Thread.sleep(1000);
        return  string;
    }


}
