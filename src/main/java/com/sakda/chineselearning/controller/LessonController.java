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
import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.dto.LessonDetailDTO;
import com.sakda.chineselearning.dto.QuestionDTO;
import com.sakda.chineselearning.dto.QuizDTO;
import com.sakda.chineselearning.service.LessonService;
import com.sakda.chineselearning.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    
    private final QuestionService questionService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<LessonDTO>> create(@RequestBody LessonDTO lessonDTO) {
        LessonDTO createdLesson = lessonService.create(lessonDTO);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lesson created successfully", createdLesson)
        );
    }
    
    @PostMapping("/{lessonId}/questions")
    public ResponseEntity<ApiResponse<QuestionDTO>> createQuestionForLesson(
    		@PathVariable Long lessonId,
    		@RequestBody QuestionDTO questionDTO
    ) {
    	
    	QuestionDTO createdQuestion = questionService.createQuestionForLesson(lessonId, questionDTO);
    	
    	return ResponseEntity.ok(
    			new ApiResponse<>(
    					true,
    					"Question created for lesson successfully",
    					createdQuestion
    					)
    		);
    }
    
    @GetMapping("/{lessonId}/quiz")
    public ResponseEntity<ApiResponse<QuizDTO>> getQuizByLessonId(
            @PathVariable Long lessonId
    ) {
    	QuizDTO quizDTO =
    			lessonService.getQuizByLessonId(lessonId);
    	
    	return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Quiz retrieved successfully",
                        quizDTO
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LessonDTO>>> getAll() {
        List<LessonDTO> lessons = lessonService.getAll();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lessons retrieved successfully", lessons)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonDTO>> getById(@PathVariable Long id) {
        LessonDTO lesson = lessonService.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lesson retrieved successfully", lesson)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonDTO>> update(
            @PathVariable Long id,
            @RequestBody LessonDTO lessonDTO) {

        LessonDTO updatedLesson = lessonService.update(id, lessonDTO);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lesson updated successfully", updatedLesson)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        lessonService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lesson deleted successfully", null)
        );
    }
    
    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<LessonDetailDTO>> getLessonDetails(@PathVariable Long id) {
    	
    	LessonDetailDTO lessonDetails = lessonService.getLessonDetails(id);
    	
    	return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Lesson details retrieved successfully",
                        lessonDetails
                )
        );
    }
    
}