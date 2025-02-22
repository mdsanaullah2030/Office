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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigaretion {
    @Autowired
    private  UserService userService;
    @Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public HttpSecurity filterChain(HttpSecurity http)throws Exception{
        return
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(
                                req ->
                                        req.requestMatchers("/api/UserRegistration/save", "/api/login",
                                                         "/images/**")
                                                .permitAll()




                                                .requestMatchers("/api/UserRegistration/get", "/api/nominee/get","api/loan/get","api/Balance/get")
                                                .hasAnyAuthority("ADMIN")


                                                .requestMatchers("/api/UserRegistration/update/{id}","/api/nominee/updateNominee/{id}","/api/nominee/save",
                                                        "/api/Balance","/api/Balance/save","/api/Balance/{id}",

                                                       "/api/loan/save","/api/loan/{id}",

                                                     "/api/Referral/save"
                                                   )
                                                .hasAuthority("USER")

                        );

    }
    //Usar Password save Time Number To BCrypt Password Encoder//
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //If User Loging Time check Authentication //
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

}
