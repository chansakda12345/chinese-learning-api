package com.sakda.chineselearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.RegisterRequest;
import com.sakda.chineselearning.dto.RegisterResponse;
import com.sakda.chineselearning.service.AuthService;
import com.sakda.chineselearning.dto.LoginRequest;
import com.sakda.chineselearning.dto.LoginResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(
                authService.register(request)
        );
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    	
    	return ResponseEntity.ok(
                authService.login(request)
        );  
    }
}