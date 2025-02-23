package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.AuthenticationResponse;
import com.saverfavor.microbank.entity.Role;
import com.saverfavor.microbank.entity.Token;
import com.saverfavor.microbank.entity.UserRegistration;
import com.saverfavor.microbank.jwt.JwtAuthenticationFilter;
import com.saverfavor.microbank.jwt.JwtService;
import com.saverfavor.microbank.repository.TokenRepository;
import com.saverfavor.microbank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private   PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  TokenRepository tokenRepository;
    @Autowired
    private   AuthenticationManager authenticationManager;



    private void saveUserToken(String jwt, UserRegistration userRegistration) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLogout(false);
        token.setUserRegistration(userRegistration);

        tokenRepository.save(token);
    }


    private void revokeAllTokenByUser(UserRegistration userRegistration) {

        List<Token> validTokens = tokenRepository.findAllTokensByUser(userRegistration.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        // Set all valid tokens for the user to logged out
        validTokens.forEach(t -> {
            t.setLogout(true);
        });

        // Save the changes to the tokens in the token repository
        tokenRepository.saveAll(validTokens);
    }




    public AuthenticationResponse register(UserRegistration userRegistration) {

        // Check if the user already exists
        if (userRepository.findByEmail(userRegistration.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");

        }

        // Create a new user entity and save it to the database

        userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        userRegistration.setRole(Role.valueOf("USER"));


        userRepository.save(userRegistration);

        // Generate JWT token for the newly registered user
        String jwt = jwtService.generateToken(userRegistration);

        // Save the token to the token repository
        saveUserToken(jwt, userRegistration);


        return new AuthenticationResponse(jwt, "User registration was successful");
    }




    // Method to authenticate a user
    public AuthenticationResponse authenticate(UserRegistration request) {

        // Authenticate user credentials using Spring Security's AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // Retrieve the user from the database
        UserRegistration userRegistration = (UserRegistration) userRepository.findByEmail(request.getUsername()).orElseThrow();

        // Generate JWT token for the authenticated user
        String jwt = jwtService.generateToken(userRegistration);

        // Revoke all existing tokens for this user
        revokeAllTokenByUser(userRegistration);

        // Save the new token to the token repository
        saveUserToken(jwt, userRegistration);


        return new AuthenticationResponse(jwt, "User login was successful");
    }

    }

