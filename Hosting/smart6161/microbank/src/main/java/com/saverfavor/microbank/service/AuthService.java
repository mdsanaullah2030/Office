package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.*;
import com.saverfavor.microbank.jwt.JwtService;
import com.saverfavor.microbank.repository.TokenRepository;
import com.saverfavor.microbank.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t -> t.setLoggedOut(true));
        tokenRepository.saveAll(validTokens);
    }

    public AuthenticationResponse register(User user) {
        if (userRepository.findByEmail(user.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setLock(true);
        user.setActive(false);
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);
        sendActivationEmail(user);
        return new AuthenticationResponse(jwt, "User registration was successful");
    }

    public AuthenticationResponse registerAdmin(User user) {
        if (userRepository.findByEmail(user.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        user.setLock(true);
        user.setActive(false);
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);
        sendActivationEmail(user);
        return new AuthenticationResponse(jwt, "User registration was successful");
    }




    private void sendActivationEmail(User user) {
        String activationLink = "http://108.181.173.121:6161/activate/" + user.getId();
        String mailText = "Dear " + user.getName() + ", your registration on saverFaver.com is successful! "
                + "Activate your account here: " + activationLink + ". If you didn’t sign up, ignore this message.";
        String subject = "Confirm User Account";
        try {
            emailService.sendSimpleEmail(user.getEmail(), subject, mailText);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }










    public String activateUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not Found with this ID"));
        if (user != null) {
            user.setActive(true);
            user.setLock(false);
            userRepository.save(user);
            return "User activated successfully!";
        } else {
            return "Invalid activation token!";
        }
    }


    public AuthenticationResponse authenticate(User request) {
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Ensure user is activated
        if (!user.isEnabled()) {
            return new AuthenticationResponse(null, "User is not activated.");
        }

        // ✅ Ensure account is not locked
        if (!user.isAccountNonLocked()) {
            return new AuthenticationResponse(null, "User account is locked.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );
        } catch (Exception e) {
            return new AuthenticationResponse(null, "Invalid username or password");
        }

        // ✅ Generate JWT token only if checks passed
        String jwt = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt, "User login was successful");
    }





}