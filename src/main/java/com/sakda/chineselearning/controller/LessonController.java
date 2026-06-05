package com.sakda.chineselearning.controller;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sakda.chineselearning.dto.LessonDetailDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<ApiResponse<LessonDTO>> create(@RequestBody LessonDTO lessonDTO) {
        LessonDTO createdLesson = lessonService.create(lessonDTO);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lesson created successfully", createdLesson)
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