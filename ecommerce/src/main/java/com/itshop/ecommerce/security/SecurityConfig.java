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
                                                        "/activate/**","/images/**","/api/HomePageImage/getall","/api/HomePageImage/get/{id}"

                                                        ,"/api/ServiceFeature/get",

                                                      "/api/brands/get/all","/api/brands/get/{id}","/api/brands/update/{id}",

                                                        "/api/branches/get/all","/api/branches/getID/{id}",




                                                        "/api/catagories/get/{id}",

                                                        "/api/findCatagories/name","/api/catagories/get",

                                                        "/api/Product/getall","/api/Product/get/{id}","/api/Product/update/{id}",

                                                        "/api/catagorybyproduct/id","/api/catagorybyproduct/name",

                                                        "/api/productDetails/getall","/api/productDetails/byCategory/{catagoryId}",

                                                        "/api/productDetails/{id}","/api/productDetails/byName/{name}","/api/productDetails/Brand/get/ById/{id}"

                                                        ,"/api/productDetails/Product/get/ById/{id}",



                                                        "/api/PcBuilder/Allget","/api/PcForPartAdd/get/{id}",

                                                        "/api/PcForPartAdd/get/{id}","/api/PcForPartAdd/get","/api/PcForPartAdd/getPcBuilder/Byid/{id}"





                                                ,"/api/productDetails/filter","/api/pcparts/filter","/api/ccbuilder/items/filter",

                                                        "/api/aboutus/get","/api/aboutus/get/{id}",




                                             "/api/media/get"


                                                , "/api/ccbuilder/get/{id}","/api/ccbuilder/get"




                                                ,"/api/items/get","/api/items/get/{id}",
                                                "/api/CCBuilder/Item/Ditels/get",



                                                        "/api/CCBuilder/Ditels/itemId/Idby/get/{id}","/api/CCBuilder/Ditels/ccBuilder/get/ById/{id}",
   "/api/CCBuilder/Item/Ditels/get/{id}",






   "/api/product/items/get" , "/api/product/items/{id}" ,
                                                        "/api/product/items/delete/{id}","/api/media/get/{id}"
















                                                )
                                                .permitAll()


                                                .requestMatchers(   "/api/productdetails/orders/save","/api/pcforpart/orders/save",



                                                        "/api/orders/delete/{id}","/api/Order/getByUser/{userId}"

                                                        ,"/api/CCItem/Bulder/orders/save",


                                                        "/api/orders/AddToCadrt/productdetails/save","/api/orders/AddToCard/pcpart/save",

                                                        "/api/CCItemBuilder/AddToCart/save","/api/orders/AddToCadrt/CCItemBuilder/save",

                                                        "/api/productdetails/AddTocart/save","/api/pcforpart/AddToCart/save",
                                                        "/api/addcart/user/get/{userId}","/api/addcart/remove/{cartId}","/api/AddTocart/get/{id}"


                                                )
                                                .hasAuthority("USER")


                                                .requestMatchers(






                                                        "/api/userRegistration/get",


                                                        "/api/HomePageImage/save","/api/HomePageImage/update/{is}","/api/HomePageImage/delete/{id}",


                                                        "/api/branches/save","/api/branches/update/{id}","/api/branches/delete/{id}",


                                                        "/api/ServiceFeature/save","/api/ServiceFeature/updete/{id}",
                                                        "/api/ServiceFeature/delete/{id}",

                                                        "/api/brands/save","/api/brands/delete/{id}",

                                                        "/api/catagories/save" ,"/api/catagories/update/{id}",

                                                        "/api/Product/save","/api/Product/update/{id}"

                                                        ,"/api/ProductDetails/save",


                                                          "/api/orders/all","/api/orders/{id}",
                                                        "/api/orders/delete/{id}","/api/orders/updete/{id}",

                                                        "/api/PcBuilder/save",

                                                        "/api/PcForPartAdd/save",
                                                        "/api/pcforpartadd/update/{id}", "/api/PcForPartAdd/delete/{id}"



                                                        ,"/api/aboutus/save","/api/aboutus/delete/{id}",
                                                        "/api/aboutus/updete/{id}"


                                                        ,"/api/media/Delete/{id}",   "/api/media/save",


                                                           "/api/ccbuilder/updete/","/api/ccbuilder/updete/data/{id}","/api/ccbuilder/save"

                                                        ,"/api/items/update/{id}",
                                                        "/api/items/delete/{id}","/api/items/save",


                                                        "/api/CCBuilder/Item/Ditels/save", "/api/CCBuilder/Item/Ditels/update/{id}","/api/CCBuilder/Item/Ditels/delete/{id}"
                                                        ,"/api/product/Ditels/delete/{id}","/api/ProductDetails/update/{id}"

                                                        ,"/api/product/items/save","/api/product/items/update/{id}"

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



