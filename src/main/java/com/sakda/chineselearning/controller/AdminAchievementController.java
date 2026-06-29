package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.AchievementDTO;
import com.sakda.chineselearning.dto.AchievementRequestDTO;
import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.service.AchievementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Achievement API", description = "Admin achievement management")
@RestController
@RequestMapping("/api/v1/admin/achievements")
@RequiredArgsConstructor
public class AdminAchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Get admin achievements")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AchievementDTO>>> getAllAchievements() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievements retrieved successfully",
                        achievementService.getAllAchievements()
                )
        );
    }

    @Operation(summary = "Get admin achievement by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AchievementDTO>> getAchievementById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievement retrieved successfully",
                        achievementService.getAchievementById(id)
                )
        );
    }

    @Operation(summary = "Create admin achievement")
    @PostMapping
    public ResponseEntity<ApiResponse<AchievementDTO>> createAchievement(
            @RequestBody AchievementRequestDTO dto
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievement created successfully",
                        achievementService.createAchievement(dto)
                )
        );
    }

    @Operation(summary = "Update admin achievement")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AchievementDTO>> updateAchievement(
            @PathVariable Long id,
            @RequestBody AchievementRequestDTO dto
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievement updated successfully",
                        achievementService.updateAchievement(id, dto)
                )
        );
    }

    @Operation(summary = "Delete admin achievement")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAchievement(
            @PathVariable Long id
    ) {
        achievementService.deleteAchievement(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievement deleted successfully",
                        null
                )
        );
    }

    @Operation(summary = "Activate admin achievement")
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<AchievementDTO>> activateAchievement(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievement activated successfully",
                        achievementService.activateAchievement(id)
                )
        );
    }

    @Operation(summary = "Deactivate admin achievement")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<AchievementDTO>> deactivateAchievement(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin achievement deactivated successfully",
                        achievementService.deactivateAchievement(id)
                )
        );
    }
}