package com.sakda.chineselearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.TelegramChatIdRequest;
import com.sakda.chineselearning.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
		name = "User API",
		description = "User entity control"
)
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@Operation(summary = "Update telegram chat id of User")
	@PutMapping("/me/telegram-chat-id")
	public ResponseEntity<ApiResponse<Void>> updateTelegramChatId(@RequestBody TelegramChatIdRequest request) {
		
		userService.updateTelegramChatId(request);
		
		return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Telegram chat ID updated successfully",
                        null
                )
        );
	}
	
	

}
