package com.saverfavor.microbank.notification;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public String send(@Payload String message) {
        return message;
    }

    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload PrivateMessage message) {
        simpMessagingTemplate.convertAndSendToUser(
                message.getTo(), "/specific", message
        );
    }

}
