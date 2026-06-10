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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class OptionController {
	
	private final OptionService optionService;
	
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
