package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.QuestionDTO;
import com.sakda.chineselearning.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
	
	private final QuestionService questionService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<QuestionDTO>> createQuestion(@RequestBody QuestionDTO questionDTO) {
		
		QuestionDTO question = questionService.createQuestion(questionDTO);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Question created successfully",
						question
						)
				);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<QuestionDTO>>> getQuestions() {
		
		List<QuestionDTO> questions = questionService.getQuestions();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Questions retrieved successfully",
						questions
						)
				);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<QuestionDTO>> getQuestionById(@PathVariable Long id) {
		
		QuestionDTO question = questionService.getQuestionById(id);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Question retrieved successfully",
						question
						)
				);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<QuestionDTO>> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
		
		QuestionDTO updateQuestion = questionService.updateQuestion(id, questionDTO);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Question updated successfully",
						updateQuestion
						)
				);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
		
		questionService.deleteQuestion(id);
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Question deleted successfully",
						null
						)
				);
	}

}
