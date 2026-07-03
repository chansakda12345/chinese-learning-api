package com.sakda.chineselearning.telegram.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.telegram.dto.TelegramPostDTO;
import com.sakda.chineselearning.telegram.service.TelegramPostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Telegram Post API", description = "Endpoints for admins to manage marketing posts")
@RestController
@RequestMapping("/api/v1/admin/telegram/posts")
@RequiredArgsConstructor
public class AdminTelegramPostController {
	
	private final TelegramPostService telegramPostService;
	
	@Operation(summary = "Create a new Telegram marketing post")
    @PostMapping
    public ResponseEntity<ApiResponse<TelegramPostDTO>> createPost(
    		@Valid @RequestBody TelegramPostDTO postDTO
    ) {
		TelegramPostDTO created = telegramPostService.createPost(postDTO);

		return ResponseEntity.ok(
                new ApiResponse<>(true, "Telegram post created successfully", created)
        );
	}
	
	@Operation(summary = "Get all Telegram marketing posts")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TelegramPostDTO>>> getAllPosts() {
		
		List<TelegramPostDTO> posts = telegramPostService.getAllPosts();
		
		return ResponseEntity.ok(
                new ApiResponse<>(true, "Telegram posts retrieved successfully", posts)
        );
	}
	
	@Operation(summary = "Send a Telegram post immediately")
	@PostMapping({"/{id}/send"})
	public ResponseEntity<ApiResponse<Void>> sendPost(
			@PathVariable Long id
	) {
		telegramPostService.sendPost(id);

		return ResponseEntity.ok(
                new ApiResponse<>(true, "Telegram post broadcast triggered successfully", null)
        );
	}
	

}
