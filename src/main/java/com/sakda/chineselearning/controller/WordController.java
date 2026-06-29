package com.sakda.chineselearning.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.service.WordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Word API", description = "Student word learning APIs")
@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
public class WordController {
	
	private final WordService wordService;
	
	@Operation(summary = "Get all Chinese words")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<WordDTO>>> getAll(
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir
			
			) {
		Page<WordDTO> words = wordService.getAll(keyword, page, size, sortBy, sortDir);
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Words retrieved successfully",
						words
						)
				);
	}
	
	@Operation(summary = "Get Chinese word by ID")
	@GetMapping("/{id}")
	public  ResponseEntity<ApiResponse<WordDTO>> getById(@PathVariable Long id) {
		WordDTO word = wordService.getById(id);
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Word Retrieved successfully",
						word
						)
				);			
	}
}
