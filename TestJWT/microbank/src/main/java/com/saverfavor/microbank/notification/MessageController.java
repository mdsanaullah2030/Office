package com.saverfavor.microbank.notification;

import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, UserRepository userRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepository = userRepository;
    }

    // Send a message to all users
    @MessageMapping("/sendToAll")
    public void sendToAllUsers(@Payload String message) {
        simpMessagingTemplate.convertAndSend("/all/messages", message);
    }

    // Send a private message to a specific user
    @MessageMapping("/sendToUser")
    public void sendToSpecificUser(@Payload PrivateMessage message) {
        simpMessagingTemplate.convertAndSendToUser(
                message.getTo(), "/specific", message.getContent()
        );
    }

    // API to send notifications to all users
    public void sendNotificationToAllUsers(String notification) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            simpMessagingTemplate.convertAndSendToUser(user.getUserid(), "/specific", notification);
        }
    }

    // API to send notification to a specific user by userId
    public void sendNotificationToSpecificUser(String userId, String notification) {
        simpMessagingTemplate.convertAndSendToUser(userId, "/specific", notification);
    }
}
