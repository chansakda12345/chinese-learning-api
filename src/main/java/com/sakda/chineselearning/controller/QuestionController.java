package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.QuestionDTO;
import com.sakda.chineselearning.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Question API",
	    description = "Manage quiz questions for lessons"
	)
@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
	
	private final QuestionService questionService;
	
	@Operation(summary = "Get all questions")
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
	
	@Operation(summary = "Get question by ID")
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
	
	@Operation(summary = "Update question")
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
	
	@Operation(summary = "Delete question")
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
