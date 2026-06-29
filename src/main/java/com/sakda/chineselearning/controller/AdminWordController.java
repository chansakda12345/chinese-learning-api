package com.sakda.chineselearning.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.service.WordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Word API", description = "Admin word management")
@RestController
@RequestMapping("/api/v1/admin/words")
@RequiredArgsConstructor
public class AdminWordController {

    private final WordService wordService;

    @Operation(summary = "Get admin words")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<WordDTO>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<WordDTO> words =
                wordService.getAll(keyword, page, size, sortBy, sortDir);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin words retrieved successfully",
                        words
                )
        );
    }

    @Operation(summary = "Get admin word by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WordDTO>> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin word retrieved successfully",
                        wordService.getById(id)
                )
        );
    }

    @Operation(summary = "Create admin word")
    @PostMapping
    public ResponseEntity<ApiResponse<WordDTO>> create(
            @Valid @RequestBody WordDTO wordDTO
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin word created successfully",
                        wordService.create(wordDTO)
                )
        );
    }

    @Operation(summary = "Update admin word")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WordDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody WordDTO wordDTO
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin word updated successfully",
                        wordService.update(id, wordDTO)
                )
        );
    }

    @Operation(summary = "Delete admin word")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {
        wordService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin word deleted successfully",
                        null
                )
        );
    }

    @Operation(summary = "Assign admin word to lesson")
    @PutMapping("/{wordId}/lesson/{lessonId}")
    public ResponseEntity<ApiResponse<WordDTO>> assignLesson(
            @PathVariable Long wordId,
            @PathVariable Long lessonId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin word assigned to lesson successfully",
                        wordService.assignLesson(wordId, lessonId)
                )
        );
    }
}