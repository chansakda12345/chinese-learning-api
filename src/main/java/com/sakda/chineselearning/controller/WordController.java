package com.sakda.chineselearning.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.service.WordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Word API",
	    description = "Manage Chinese words"
	)
@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
public class WordController {
	
	private final WordService wordService;
	
	@Operation(summary = "Create a new Chinese word")
	@PostMapping
	public ResponseEntity<ApiResponse<WordDTO>> create(@Valid @RequestBody WordDTO wordDTO) {
		WordDTO createWord = wordService.create(wordDTO);
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Word created successfully",
						createWord
						)
				);
	}
	
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
	
	@Operation(summary = "Update Chinese word")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<WordDTO>> updateById(@PathVariable Long id, @Valid @RequestBody WordDTO wordDTO) {
		WordDTO updateWord = wordService.update(id, wordDTO);
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Word updated sucessfully",
						updateWord
						)
				);
	}
	
	@Operation(summary = "Delete Chinese word")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

	    wordService.delete(id);

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Word deleted successfully",
	                    null
	            )
	    );
	}
	
	@Operation(summary = "Assign a Chinese word to a lesson")
	@PutMapping("/{wordId}/lesson/{lessonId}")
	public ResponseEntity<ApiResponse<WordDTO>> assignLesson(
			@PathVariable Long wordId,
			@PathVariable Long lessonId
	) {
		WordDTO assignLesson = wordService.assignLesson(wordId, lessonId);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Word assigned to lesson successfully",
						assignLesson
						)
				);
	}
	
	@Operation(summary = "Upload audio pronunciation for a word")
	@PostMapping("{id}/audio")
	public ResponseEntity<ApiResponse<WordDTO>> uploadAudio(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		
		WordDTO word = wordService.uploadAudio(id, file);
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Audio uploaded successfully",
	                    word
	            )
	    );
	}
	

}
