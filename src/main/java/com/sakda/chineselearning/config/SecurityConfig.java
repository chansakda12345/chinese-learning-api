package com.sakda.chineselearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login"
                        ).permitAll()
                        
                        .requestMatchers(
                        		HttpMethod.GET,
                        		"/api/v1/words/**",
                        		"/api/v1/lessons/**"
                        ).permitAll()
                        
                        .requestMatchers(
                        		HttpMethod.POST,
                        		"/api/v1/words/**",
                        		"/api/v1/lessons/**"
                        ).hasRole("ADMIN")
                        
                        .requestMatchers(
                        		HttpMethod.PUT,
                        		"/api/v1/words/**",
                        		"/api/v1/lessons/**"
                         ).hasRole("ADMIN")
                        
                        .requestMatchers(
                        		HttpMethod.DELETE,
                        		"/api/v1/words/**",
                        		"/api/v1/lessons/**"
                         ).hasRole("ADMIN")
                        
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}