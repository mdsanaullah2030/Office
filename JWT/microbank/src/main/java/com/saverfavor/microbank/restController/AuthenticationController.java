package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.AuthenticationResponse;
import com.saverfavor.microbank.entity.UserRegistration;
import com.saverfavor.microbank.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AuthenticationController {




@Autowired
    private  AuthService authService;

    @PostMapping("/UserRegistration")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRegistration request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }



    @PostMapping("/api/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserRegistration request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }


}
