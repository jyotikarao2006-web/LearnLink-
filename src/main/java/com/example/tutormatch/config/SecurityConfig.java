package com.example.tutormatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable CSRF for REST APIs
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // allow everything
            )
            .formLogin(form -> form.disable()) // disable Spring's login form
            .httpBasic(basic -> basic.disable()); // disable basic auth
        return http.build();
    }
}
