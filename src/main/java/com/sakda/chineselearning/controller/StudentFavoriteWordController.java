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
import com.sakda.chineselearning.dto.StudentFavoriteWordDTO;
import com.sakda.chineselearning.service.StudentFavoriteWordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Student Favority Word API", description = "Student save favorite words")
@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class StudentFavoriteWordController {
	
	private final StudentFavoriteWordService studentFavoriteWordService;
	
	@Operation(summary = "Save favorite word")
	@PostMapping("/words/{wordId}")
	public ResponseEntity<ApiResponse<Void>> saveFavoriteWord(@PathVariable Long wordId) {
		
		studentFavoriteWordService.saveFavoriteWord(wordId);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Word saved sucessfully", null));
	}
	
	@Operation(summary = "Get all favorite words")
	@GetMapping
	public ResponseEntity<ApiResponse<List<StudentFavoriteWordDTO>>> getMyFavoriteWords() {
		
		List<StudentFavoriteWordDTO> favorites = studentFavoriteWordService.getMyFavoriteWords();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Favorite words retrieved successfully",
						favorites
						)
				);
	}
	
	@Operation(summary = "Delete favorite word")
	@DeleteMapping("/{favoriteId}") 
	public ResponseEntity<ApiResponse<Void>> removeFavoriteWord(@PathVariable Long favoriteId) {
		
		studentFavoriteWordService.removeFavoriteWord(favoriteId);
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Favorite word removed successfully",
	                    null
	            )
	    );
	}
	
	@Operation(summary = "Last review of favorite wor")
	@PostMapping("/{favoriteId}/review")
	public ResponseEntity<ApiResponse<Void>> reviewFavoriteWord(@PathVariable Long favoriteId) {
		
		studentFavoriteWordService.reviewFavoriteWord(favoriteId);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Favorite word reviewed successfully", null));
	}
	
	@Operation(summary = "Get favorite word due reviewd")
	@GetMapping("/review-due")
	public ResponseEntity<ApiResponse<List<StudentFavoriteWordDTO>>> getReviewDueWords() {
		
		List<StudentFavoriteWordDTO> reviewDueWords = studentFavoriteWordService.getReviewDueWords();
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Retrieve favorite word due review sucessfully", reviewDueWords));
	}

}
