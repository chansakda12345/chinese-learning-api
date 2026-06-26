package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.AchievementDTO;
import com.sakda.chineselearning.dto.AchievementProgressDTO;
import com.sakda.chineselearning.dto.AchievementRequestDTO;
import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.MyAchievementDTO;
import com.sakda.chineselearning.service.AchievementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Achievement API", description = "Create and Get achievement")
@RestController
@RequestMapping("/api/v1/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Create achievement")
    @PostMapping
    public ResponseEntity<ApiResponse<AchievementDTO>> createAchievement(
            @RequestBody AchievementRequestDTO dto
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Achievement created successfully",
                        achievementService.createAchievement(dto)
                )
        );
    }

    @Operation(summary = "Get all achievements")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AchievementDTO>>> getAllAchievements() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Achievements retrieved successfully",
                        achievementService.getAllAchievements()
                )
        );
    }

    @Operation(summary = "Update achievement")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AchievementDTO>> updateAchievement(
            @PathVariable Long id,
            @RequestBody AchievementRequestDTO dto
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Achievement updated successfully",
                        achievementService.updateAchievement(id, dto)
                )
        );
    }
    
    @Operation(summary = "Get current user's achievements")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<MyAchievementDTO>>> getMyAchievements() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "My achievements retrieved successfully",
                        achievementService.getMyAchievements()
                )
        );
    }
    
    @Operation(summary = "Get current user's achievement progressing")
    @GetMapping("/progress")
    public ResponseEntity<ApiResponse<List<AchievementProgressDTO>>> getAchievementProgress() {
    	
    	return ResponseEntity.ok(new ApiResponse<>(true, "Achievement progress retrieved successfully", achievementService.getAchievementProgress()));
    	
    }
    
}