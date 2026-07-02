package com.sakda.chineselearning.telegram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.telegram.dto.TelegramLinkRequestDTO;
import com.sakda.chineselearning.telegram.dto.TelegramLinkResponseDTO;
import com.sakda.chineselearning.telegram.dto.TelegramVerificationCodeResponseDTO;
import com.sakda.chineselearning.telegram.service.TelegramLinkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Telegram Account Linking")
@RestController
@RequestMapping("/api/v1/telegram/link")
@RequiredArgsConstructor
public class TelegramLinkController {

    private final TelegramLinkService telegramLinkService;
    
    @Operation(summary = "Generate Telegram verification code")
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<TelegramVerificationCodeResponseDTO>> generateVerificationCode() {

        TelegramVerificationCodeResponseDTO response =
                new TelegramVerificationCodeResponseDTO();

        response.setCode(telegramLinkService.generateVerificationCode());

        return ResponseEntity.ok(
        		new ApiResponse<>(
        				true,
        				"Generate Telegram verification code successfully",
        				response
        				)
        		);
    }
    
    @Operation(summary = "Verify and link Telegram account")
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<TelegramLinkResponseDTO>> verifyTelegramLink(
            @RequestBody TelegramLinkRequestDTO request
    ) {
        TelegramLinkResponseDTO response =
                telegramLinkService.verifyTelegramLink(request);

        return ResponseEntity.ok(
        		new ApiResponse<>(
        				true,
        				"Generate Telegram verification code successfully",
        				response
        				)
        		);
    }
}