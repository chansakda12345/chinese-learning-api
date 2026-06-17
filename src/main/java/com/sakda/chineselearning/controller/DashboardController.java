package com.sakda.chineselearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.ReviewStatsDTO;
import com.sakda.chineselearning.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Dashboard statistics API", description = "Get stats of Student's favorite words")
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
	
	private final DashboardService dashboardService;
	
	@Operation(summary = "Get review statistics")
	@GetMapping("/review-stats")
	public ResponseEntity<ApiResponse<ReviewStatsDTO>> getReviewStats() {
		
		ReviewStatsDTO reviewStats = dashboardService.getReviewStats();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Review statistics retrieved successfully",
						reviewStats
						)
				);
	}

}
