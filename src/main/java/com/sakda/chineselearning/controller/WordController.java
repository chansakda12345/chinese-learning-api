package com.sakda.chineselearning.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.service.WordService;
import jakarta.validation.Valid;
import com.sakda.chineselearning.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
public class WordController {
	
	private final WordService wordService;
	
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

}
