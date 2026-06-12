package com.sakda.chineselearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.OptionDTO;
import com.sakda.chineselearning.service.OptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Option API",
	    description = "Manage answer options for quiz questions"
	)
@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class OptionController {
	
	private final OptionService optionService;
	
	@Operation(summary = "Create an option for a question")
	@PostMapping("/{questionId}/options")
	public ResponseEntity<ApiResponse<OptionDTO>> createOption(
			@PathVariable Long questionId,
			@RequestBody OptionDTO optionDTO
			) {
		
		OptionDTO createdOption = optionService.createOption(questionId, optionDTO);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Option created successfully",
						createdOption
						)
				);
	}

}
