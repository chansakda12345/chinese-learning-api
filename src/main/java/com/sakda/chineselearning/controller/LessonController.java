package com.sakda.chineselearning.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.dto.LessonDetailDTO;
import com.sakda.chineselearning.dto.QuizDTO;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.service.LessonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Lesson API", description = "Student lesson learning APIs")
@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

	private final LessonService lessonService;

	@Operation(summary = "Get quiz questions for a lesson")
	@GetMapping("/{lessonId}/quiz")
	public ResponseEntity<ApiResponse<QuizDTO>> getQuizByLessonId(@PathVariable Long lessonId) {
		QuizDTO quizDTO = lessonService.getQuizByLessonId(lessonId);

		return ResponseEntity.ok(new ApiResponse<>(true, "Quiz retrieved successfully", quizDTO));
	}

	@Operation(summary = "Get lesson by ID")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<LessonDTO>> getById(@PathVariable Long id) {
		LessonDTO lesson = lessonService.getById(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson retrieved successfully", lesson));
	}

	@Operation(summary = "Get detailed lesson information")
	@GetMapping("/{id}/details")
	public ResponseEntity<ApiResponse<LessonDetailDTO>> getLessonDetails(@PathVariable Long id) {

		LessonDetailDTO lessonDetails = lessonService.getLessonDetails(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson details retrieved successfully", lessonDetails));
	}
	
	@Operation(summary = "Get all lessons OR Search By Level, Keyword, and Sort")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<LessonDTO>>> getAll(
			@RequestParam(required = false) HskLevel level,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "title") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		
		Page<LessonDTO> lessons = lessonService.getAll(level, keyword, page, size, sortBy, sortDir);
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Lessons retrieved successfully",
	                    lessons
	            )
	    );
	}

}