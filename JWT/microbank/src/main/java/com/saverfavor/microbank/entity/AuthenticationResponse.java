package com.saverfavor.microbank.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String message;

    // Manually defined constructor
    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
