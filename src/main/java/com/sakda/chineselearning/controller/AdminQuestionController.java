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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
        name = "Admin Question API",
        description = "Admin question management"
)
@RestController
@RequestMapping("/api/v1/admin/questions")
@RequiredArgsConstructor
public class AdminQuestionController {
	
	private final QuestionService questionService;
	
	@Operation(summary = "Get admin questions")
	@GetMapping
	public ResponseEntity<ApiResponse<List<QuestionDTO>>> getQuestions() {
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Admin questions retrieved successfully",
	                    questionService.getQuestions()
	            )
	    );
	}
	
	@Operation(summary = "Get admin question by id")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<QuestionDTO>> getQuestionById(@PathVariable Long id) {
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Admin question retrieved successfully",
						questionService.getQuestionById(id)
				)
		);
	}
	
	@Operation(summary = "Update admin question")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<QuestionDTO>> updateQuestion(
	        @PathVariable Long id,
	        @RequestBody QuestionDTO questionDTO
	) {

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Admin question updated successfully",
	                    questionService.updateQuestion(id, questionDTO)
	            )
	    );
	}
	
	@Operation(summary = "Delete admin question")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteQuestion(
	        @PathVariable Long id
	) {
	    questionService.deleteQuestion(id);

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Admin question deleted successfully",
	                    null
	            )
	    );
	}
	
	@Operation(summary = "Create admin question for lesson")
	@PostMapping("/lesson/{lessonId}")
	public ResponseEntity<ApiResponse<QuestionDTO>> createQuestionForLesson(
			@PathVariable Long lessonId,
			@RequestBody QuestionDTO questionDTO
			) {
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Admin question created for lesson successfully",
	                    questionService.createQuestionForLesson(lessonId, questionDTO)
	            )
	    );
	}

}
