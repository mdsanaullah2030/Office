package com.saverfavor.microbank.notification;

import com.saverfavor.microbank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    // Send notification to all users
    @PostMapping("/sendToAll")
    public ResponseEntity<String> sendToAll(@RequestBody String message) {
        userService.getAllUserRegistrations().forEach(user ->
                simpMessagingTemplate.convertAndSendToUser(user.getUserid(), "/specific", message)
        );
        return ResponseEntity.ok("Notification sent to all users");
    }

    // Send notification to a specific user
    @PostMapping("/sendToUser/{userId}")
    public ResponseEntity<String> sendToUser(@PathVariable String userId, @RequestBody String message) {
        simpMessagingTemplate.convertAndSendToUser(userId, "/specific", message);
        return ResponseEntity.ok("Notification sent to user: " + userId);
    }
}
