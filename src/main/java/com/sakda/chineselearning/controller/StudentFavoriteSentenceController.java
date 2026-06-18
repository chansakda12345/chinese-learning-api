package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.StudentFavoriteSentenceDTO;
import com.sakda.chineselearning.service.StudentFavoriteSentenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	name = "Favorite sentence API",
	description = "Student with favorite sentence"
)
@RestController
@RequestMapping("/api/v1/favorite-sentences")
@RequiredArgsConstructor
public class StudentFavoriteSentenceController {
	
	private final StudentFavoriteSentenceService studentFavoriteSentenceService;
	
	@Operation(summary = "Save favorite sentence")
	@PostMapping("/{sentenceId}")
	public ResponseEntity<ApiResponse<Void>> saveFavoriteSentence(@PathVariable Long sentenceId) {
		
		studentFavoriteSentenceService.saveFavoriteSentence(sentenceId);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Favorite sentence saved successfully",
						null
						)
				);
	}
	
	@Operation(summary = "View all favorite sentences of a user")
	@GetMapping
	public ResponseEntity<ApiResponse<List<StudentFavoriteSentenceDTO>>> getMyFavoriteSentences() {
		
		List<StudentFavoriteSentenceDTO> favoriteSentences = studentFavoriteSentenceService.getMyFavoriteSentences();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Favorite sentences retrieved successfully",
						favoriteSentences
						)
				);
	}
	

	@Operation(summary = "Remove favorite sentence")
	@DeleteMapping("/{favoriteId}")
	public ResponseEntity<ApiResponse<Void>> removeFavoriteSentence(
	        @PathVariable Long favoriteId) {
		
		studentFavoriteSentenceService.removeFavoriteSentence(favoriteId);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Favorite sentence removed successfully",
						null
						)
				);
	}
	
	@Operation(summary = "Review on favorite sentence")
	@PostMapping("/{favoriteId}/review")
	public ResponseEntity<ApiResponse<Void>> reviewFavoriteSentence(@PathVariable Long favoriteId) {
		
		studentFavoriteSentenceService.reviewFavoriteSentence(favoriteId);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Favorite sentence reviewed successfully",
						null
						)
				);
	}
	
	@Operation(summary = "Get due favorite sentences")
	@GetMapping("/review-due")
	public ResponseEntity<ApiResponse<List<StudentFavoriteSentenceDTO>>> getReviewDueSentences() {
		
		List<StudentFavoriteSentenceDTO> dueSentences = studentFavoriteSentenceService.getReviewDueSentences();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Due favorite sentences retrieved successfully",
						dueSentences
						)
				);
	}

}
