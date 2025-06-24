package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.AuthenticationResponse;
import com.ecommerce.brandlyandco.entity.Role;
import com.ecommerce.brandlyandco.entity.Token;
import com.ecommerce.brandlyandco.entity.User;
import com.ecommerce.brandlyandco.jwt.JwtService;
import com.ecommerce.brandlyandco.repository.TokenRepository;
import com.ecommerce.brandlyandco.repository.UserRepository;
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


        user.setLock(false);     // Unlock user
        user.setActive(true);    // Enable user

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);



        return new AuthenticationResponse(jwt, "User registration was successful");
    }


    public AuthenticationResponse registerAdmin(User user) {
        if (userRepository.findByEmail(user.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);


        user.setLock(false);     // Unlock admin
        user.setActive(true);    // Enable admin

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);



        return new AuthenticationResponse(jwt, "Admin registration was successful");
    }




    private void sendActivationEmail(User user) {
        String activationLink = "http://localhost:2030/activate/" + user.getId();

        String subject = "Activate Your FINSYS Account";

        String mailText = "Dear " + user.getName() + ",\n\n" +
                "Your registration on FINSYS is successful!\n\n" +
                "Please click the link below to verify your email address:\n" +
                activationLink + "\n\n" +
                "If you didn’t sign up, please ignore this message.\n\n" +
                "For any assistance, feel free to reach out to our support team at help@getfinsys.com.\n\n" +
                "Thank you for choosing FINSYS.\n\n" +
                "© Financial System Solutions";

        try {
            emailService.sendSimpleEmail(user.getEmail(), subject, mailText);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send activation email", e);
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
        String identifier = request.getUsername(); // Can be email or phone number

        // Find user by email or phone number
        User user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByUsername(identifier))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate status
        if (!user.isEnabled()) {
            return new AuthenticationResponse(null, "User is not activated.");
        }

        if (!user.isAccountNonLocked()) {
            return new AuthenticationResponse(null, "User account is locked.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(), // AuthenticationManager uses email internally
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return new AuthenticationResponse(null, "Invalid username or password");
        }

        String jwt = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt, "User login was successful");
    }




}
