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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.LessonContentDTO;
import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.dto.LessonDetailDTO;
import com.sakda.chineselearning.dto.QuestionDTO;
import com.sakda.chineselearning.dto.QuizDTO;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.service.LessonService;
import com.sakda.chineselearning.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Lesson API", description = "Manage Chinese learning lessons and thumbnails")
@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

	private final LessonService lessonService;

	private final QuestionService questionService;

	@Operation(summary = "Create a new lesson")
	@PostMapping
	public ResponseEntity<ApiResponse<LessonDTO>> create(@RequestBody LessonDTO lessonDTO) {
		LessonDTO createdLesson = lessonService.create(lessonDTO);

		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson created successfully", createdLesson));
	}

	@Operation(summary = "Create question to a Chinese lesson")
	@PostMapping("/{lessonId}/questions")
	public ResponseEntity<ApiResponse<QuestionDTO>> createQuestionForLesson(@PathVariable Long lessonId,
			@RequestBody QuestionDTO questionDTO) {

		QuestionDTO createdQuestion = questionService.createQuestionForLesson(lessonId, questionDTO);

		return ResponseEntity.ok(new ApiResponse<>(true, "Question created for lesson successfully", createdQuestion));
	}

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

	@Operation(summary = "Update lesson")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<LessonDTO>> update(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {

		LessonDTO updatedLesson = lessonService.update(id, lessonDTO);

		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson updated successfully", updatedLesson));
	}

	@Operation(summary = "Delete a Chinese lesson")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		lessonService.delete(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson deleted successfully", null));
	}

	@Operation(summary = "Get detailed lesson information")
	@GetMapping("/{id}/details")
	public ResponseEntity<ApiResponse<LessonDetailDTO>> getLessonDetails(@PathVariable Long id) {

		LessonDetailDTO lessonDetails = lessonService.getLessonDetails(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson details retrieved successfully", lessonDetails));
	}

	@Operation(summary = "Upload lesson thumbnail")
	@PostMapping("/{id}/thumbnail")
	public ResponseEntity<ApiResponse<LessonDTO>> uploadThumbnail(@PathVariable Long id,
			@RequestParam("file") MultipartFile file) {

		LessonDTO lesson = lessonService.uploadThumbnail(id, file);

		return ResponseEntity.ok(new ApiResponse<>(true, "Thumbnail uploaded successfully", lesson));
	}
	
	@Operation(summary = "Update lesson content")
	@PutMapping("/{id}/content")
	public ResponseEntity<ApiResponse<LessonDTO>> updatedLesson(
			@PathVariable Long id, 
			@RequestBody LessonContentDTO lessonContentDTO
		) {
		
		LessonDTO updatedContent = lessonService.updatedLesson(id, lessonContentDTO);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Lesson content updated successfully\"", updatedContent));
	} 
	
	@Operation(summary = "Get all lessons OR Get lessons by HSK level")
	@GetMapping
	public ResponseEntity<ApiResponse<List<LessonDTO>>> getAll(@RequestParam(required = false) HskLevel level) {
		
		List<LessonDTO> lessons = lessonService.getAll(level);
		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Lessons retrieved successfully",
	                    lessons
	            )
	    );
	}

}