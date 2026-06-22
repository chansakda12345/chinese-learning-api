package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.LeaderboardDTO;
import com.sakda.chineselearning.service.LeaderboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Leaderboard API", description = "Get Leaderboard for Ranking Student")
@RestController
@RequestMapping("/api/v1/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Operation(summary = "Get global leaderboard")
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaderboardDTO>>> getLeaderboard() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaderboard retrieved successfully",
                        leaderboardService.getLeaderboard()
                )
        );
    }

    @Operation(summary = "Get top 10 leaderboard")
    @GetMapping("/top10")
    public ResponseEntity<ApiResponse<List<LeaderboardDTO>>> getTop10() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Top 10 leaderboard retrieved successfully",
                        leaderboardService.getTop10()
                )
        );
    }

    @Operation(summary = "Get current user rank")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<LeaderboardDTO>> getCurrentUserRank() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Current user rank retrieved successfully",
                        leaderboardService.getCurrentUserRank()
                )
        );
    }
}