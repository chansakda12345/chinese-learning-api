package com.sakda.chineselearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.QuizResultDTO;
import com.sakda.chineselearning.dto.QuizSubmitDTO;
import com.sakda.chineselearning.service.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Quiz API",
	    description = "Submit quizzes and calculate scores"
	)
@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {
	
	private final QuizService quizService;
	
	@Operation(summary = "Submit quiz answers and calculate score")	
	@PostMapping("/submit")
	public ResponseEntity<ApiResponse<QuizResultDTO>> submitQuiz(@RequestBody QuizSubmitDTO quizSubmitDTO) {
		
		QuizResultDTO result = quizService.submitQuiz(quizSubmitDTO);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Quiz submitted sucessfully",
						result
						)
				);
	}

}
