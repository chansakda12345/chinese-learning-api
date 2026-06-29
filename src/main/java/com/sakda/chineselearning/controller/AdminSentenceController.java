package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.service.SentenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Sentence API", description = "Admin sentence management")
@RestController
@RequestMapping("/api/v1/admin/sentences")
@RequiredArgsConstructor
public class AdminSentenceController {

    private final SentenceService sentenceService;

    @Operation(summary = "Create admin sentence")
    @PostMapping
    public ResponseEntity<ApiResponse<SentenceDTO>> createSentence(
            @RequestBody SentenceDTO sentenceDTO
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin sentence created successfully",
                        sentenceService.createSentence(sentenceDTO)
                )
        );
    }

    @Operation(summary = "Get admin sentences by lesson")
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<ApiResponse<List<SentenceDTO>>> getSentencesByLesson(
            @PathVariable Long lessonId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin lesson sentences retrieved successfully",
                        sentenceService.getSentencesByLesson(lessonId)
                )
        );
    }

    @Operation(summary = "Get admin sentence by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SentenceDTO>> getSentenceById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin sentence retrieved successfully",
                        sentenceService.getSentenceById(id)
                )
        );
    }

    @Operation(summary = "Update admin sentence")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SentenceDTO>> updateSentence(
            @PathVariable Long id,
            @RequestBody SentenceDTO sentenceDTO
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin sentence updated successfully",
                        sentenceService.updateSentence(id, sentenceDTO)
                )
        );
    }

    @Operation(summary = "Delete admin sentence")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSentence(
            @PathVariable Long id
    ) {
        sentenceService.deleteSentence(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin sentence deleted successfully",
                        null
                )
        );
    }

    @Operation(summary = "Get admin sentences")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SentenceDTO>>> getAll(
            @RequestParam(required = false) HskLevel level,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin sentences retrieved successfully",
                        sentenceService.getAll(level, keyword, page, size, sortBy, sortDir)
                )
        );
    }
}