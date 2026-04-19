package com.sistemadenuncias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para desarrollo
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // permitir todas las rutas (temporalmente)
            )
            .formLogin(form -> form.disable()); // Desactivar login de Spring

        return http.build();
    }
}
