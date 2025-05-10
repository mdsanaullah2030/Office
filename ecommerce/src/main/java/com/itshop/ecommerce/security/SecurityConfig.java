package com.itshop.ecommerce.security;

import com.itshop.ecommerce.jwt.JwtAuthenticationFilter;
import com.itshop.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class  SecurityConfig {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return
                http

                        .csrf(AbstractHttpConfigurer::disable)
                        .cors(Customizer.withDefaults())
                        .authorizeHttpRequests(

                                req ->
                                        req.requestMatchers("/login",

                                                        "/api/userRegistration","/api/userRegistration/{id}",
                                                        "/activate/**","/images/**",


                                                        "/api/catagories/get/{id}",

                                                        "/api/findCatagories/name","/api/catagories/get",

                                                        "/api/Product/getall","/api/Product/get/{id}","/api/Product/update/{id}",

                                                        "/api/catagorybyproduct/id","/api/catagorybyproduct/name",

                                                        "/api/productDetails/getall","/api/productDetails/byCategory/{catagoryId}",

                                                        "/api/productDetails/{id}","/api/productDetails/byName/{name}",



                                                        "/api/PcBuilder/Allget","/api/PcBuilder/get/{id}",

                                                        "/api/PcForPartAdd/get/{id}","/api/PcForPartAdd/get"











                                                )
                                                .permitAll()


                                                .requestMatchers(   "/api/orders/save","/api/addcart/save",
                                                        "/api/addcart/userget/{userId}","/api/remove/{cartId}"




                                                )
                                                .hasAuthority("USER")


                                                .requestMatchers(






                                                        "/api/userRegistration/get",

                                                        "/api/catagories/save" ,"/api/catagories/update/{id}",

                                                        "/api/Product/save","/api/Product/update/{id}",

                                                        "/api/ProductDetails/save",


                                                        "/api/orders/all","/api/Order/getByUser/{userId}","/api/orders/{id}",

                                                        "/api/PcBuilder/save",

                                                        "/api/PcForPartAdd/save","/api/PcForPartAdd/update/{id}","/api/PcForPartAdd/delete/{id}"





                                                )
                                                .hasAuthority("ADMIN")


                        )
                        .userDetailsService(userService)
                        .sessionManagement(
                                session ->
                                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();


    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://75.119.134.82:6161", "http://localhost:5173", "http://localhost:3000","/**" ));  // Add allowed origins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply CORS settings to all endpoints
        return source;
    }


}



