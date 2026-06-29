package com.sakda.chineselearning.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.service.LessonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Lesson API", description = "Admin lesson management")
@RestController
@RequestMapping("/api/v1/admin/lessons")
@RequiredArgsConstructor
public class AdminLessonController {

    private final LessonService lessonService;

    @Operation(summary = "Get admin lessons")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<LessonDTO>>> getAll(
            @RequestParam(required = false) HskLevel level,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<LessonDTO> lessons =
                lessonService.getAll(level, keyword, page, size, sortBy, sortDir);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin lessons retrieved successfully",
                        lessons
                )
        );
    }

    @Operation(summary = "Get admin lesson by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonDTO>> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin lesson retrieved successfully",
                        lessonService.getById(id)
                )
        );
    }

    @Operation(summary = "Create admin lesson")
    @PostMapping
    public ResponseEntity<ApiResponse<LessonDTO>> create(
            @RequestBody LessonDTO lessonDTO
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin lesson created successfully",
                        lessonService.create(lessonDTO)
                )
        );
    }

    @Operation(summary = "Update admin lesson")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonDTO>> update(
            @PathVariable Long id,
            @RequestBody LessonDTO lessonDTO
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin lesson updated successfully",
                        lessonService.update(id, lessonDTO)
                )
        );
    }

    @Operation(summary = "Delete admin lesson")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {
        lessonService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin lesson deleted successfully",
                        null
                )
        );
    }
}