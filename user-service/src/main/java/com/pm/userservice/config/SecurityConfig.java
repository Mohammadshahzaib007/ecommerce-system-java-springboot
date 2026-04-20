package com.pm.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // Allow all requests without authentication
                )
                .csrf(csrf -> csrf.disable());  // Disable CSRF for simplicity in dev

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Strength 10 is default; increase (e.g., 12) for more security at higher CPU cost
        return new BCryptPasswordEncoder();
    }
}