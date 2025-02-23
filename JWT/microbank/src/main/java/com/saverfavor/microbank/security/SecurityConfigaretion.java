package com.saverfavor.microbank.security;

import com.saverfavor.microbank.jwt.JwtAuthenticationFilter;
import com.saverfavor.microbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigaretion {
@Autowired
    private  UserService userService;
    @Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/UserRegistration/save", "/api/login", "/images/**").permitAll()
                        .requestMatchers("/api/UserRegistration/get", "/api/nominee/get", "/api/loan/get", "/api/Balance/get")
                        .hasAuthority("ADMIN")
                        .requestMatchers(
                                "/api/UserRegistration/update/{id}", "/api/nominee/updateNominee/{id}", "/api/nominee/save",
                                "/api/Balance", "/api/Balance/save", "/api/Balance/{id}",
                                "/api/loan/save", "/api/loan/{id}",
                                "/api/Referral/save"
                        )
                        .hasAuthority("USER")
                );

        return http.build();
    }

    // Password Encoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Manager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
