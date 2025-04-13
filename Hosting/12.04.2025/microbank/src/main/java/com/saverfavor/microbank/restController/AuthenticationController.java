package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.AuthenticationResponse;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.entity.Nominee;
import com.saverfavor.microbank.entity.User;

import com.saverfavor.microbank.service.AuthService;
import com.saverfavor.microbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthenticationController {
@Autowired
    private  AuthService authService;
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




    @PutMapping("/api/userRegistration/update/{id}")
    public ResponseEntity<String>updateUser(
            @PathVariable long id,
            @RequestBody User user
    )throws IOException{
        try {
            userService.updateUser(id, user);
            return ResponseEntity.ok("User updated successfully");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("User not found"));
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