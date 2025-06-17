package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.AuthenticationResponse;
import com.ecommerce.brandlyandco.entity.User;
import com.ecommerce.brandlyandco.service.AuthService;
import com.ecommerce.brandlyandco.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;





    @PostMapping("/api/userRegistration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {
        try {
            AuthenticationResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }



    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateUser(@PathVariable("id") int id) {
        String response = authService.activateUser(id);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/api/userRegistration/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        try {
            User user = userService.getUserRegistrationById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    //All Data get//
    @GetMapping("/api/userRegistration/get")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUserRegistrations();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching user data: " + e.getMessage());
        }
    }

}
