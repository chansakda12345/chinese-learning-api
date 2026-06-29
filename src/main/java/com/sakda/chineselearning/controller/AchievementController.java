package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.AchievementProgressDTO;
import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.MyAchievementDTO;
import com.sakda.chineselearning.service.AchievementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Achievement API", description = "Student achievement APIs")
@RestController
@RequestMapping("/api/v1/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

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