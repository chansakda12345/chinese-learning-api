package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.service.SentenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
		name = "Sentence API",
		description = "Manage Chinese sentences"
)
@RestController
@RequestMapping("/api/v1/sentences")
@RequiredArgsConstructor
public class SentenceController {
	
	private final SentenceService sentenceService;
	
	@Operation(summary = "Create a new sentence")
	@PostMapping
	public ResponseEntity<ApiResponse<SentenceDTO>> createSentence(@RequestBody SentenceDTO sentenceDTO) {
		SentenceDTO sentence = sentenceService.createSentence(sentenceDTO);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true, 
						"Sentence created successfully", 
						sentence
				));
	}

	@Operation(summary = "Get sentences by lesson")
	@GetMapping("lesson/{lessonId}")
	public ResponseEntity<ApiResponse<List<SentenceDTO>>> getSentencesByLesson(@PathVariable Long lessonId) {
		
		List<SentenceDTO> sentences = sentenceService.getSentencesByLesson(lessonId);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true, 
						"Lesson sentences retrieved successfully", 
						sentences
				));
	}
	
	@Operation(summary = "Get sentence by id")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<SentenceDTO>> getSentenceById(
	        @PathVariable Long id
	) {

	    SentenceDTO sentence = sentenceService.getSentenceById(id);

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Sentence retrieved successfully",
	                    sentence
	            )
	    );
	}
	
	@Operation(summary = "Update sentence")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<SentenceDTO>> updateSentence(
	        @PathVariable Long id,
	        @RequestBody SentenceDTO sentenceDTO
	) {

	    SentenceDTO sentence = sentenceService.updateSentence(id, sentenceDTO);

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Sentence updated successfully",
	                    sentence
	            )
	    );
	}
	
	@Operation(summary = "Delete sentence")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteSentence(
	        @PathVariable Long id
	) {

	    sentenceService.deleteSentence(id);

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Sentence deleted successfully",
	                    null
	            )
	    );
	}
	
	@Operation(summary = "Get all sentences with search, pagination and sorting")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<SentenceDTO>>> getAll(
			@RequestParam(required = false) HskLevel level,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir 
			
			) {
		
		Page<SentenceDTO> sentences = sentenceService.getAll(level, keyword, page, size, sortBy, sortDir);
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Sentences retrieved successfully",
	                    sentences
	            )
	    );
	}

}
