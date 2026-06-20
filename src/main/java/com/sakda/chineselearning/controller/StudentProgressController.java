package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.DashboardDTO;
import com.sakda.chineselearning.dto.HskProgressDTO;
import com.sakda.chineselearning.dto.HskRecommendationDTO;
import com.sakda.chineselearning.dto.ProgressDTO;
import com.sakda.chineselearning.service.StudentProgressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Progress API",
	    description = "Track student progress, analytics, and HSK learning path"
	)
@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class StudentProgressController {
	
	private final StudentProgressService studentProgressService;
	
	@Operation(summary = "Get current user's learning progress")
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<List<ProgressDTO>>> getMyProgress() {
		List<ProgressDTO> progresses = studentProgressService.getMyProgress();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Progress retrieved successfully",
						progresses
						)
				);
	}
	
	@Operation(summary = "Get dashboard statistics")	
	@GetMapping("/dashboard")
	public ResponseEntity<ApiResponse<DashboardDTO>> getDashboard() {
		
		DashboardDTO dashboard = studentProgressService.getDashboard();
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Dashboard retrieved successfully",
	                    dashboard
	            )
	    );
	}
	
	@Operation(summary = "Get HSK progress")
	@GetMapping("/hsk")
	public ResponseEntity<ApiResponse<List<HskProgressDTO>>> getHskProgress() {

	    List<HskProgressDTO> progress = studentProgressService.getHskProgress();

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "HSK progress retrieved successfully",
	                    progress
	            )
	    );
	}

	@Operation(summary = "Get HSK recommendation")
	@GetMapping("/recommendation")
	public ResponseEntity<ApiResponse<HskRecommendationDTO>> getRecommendation() {

	    HskRecommendationDTO recommendation =
	    		studentProgressService.getRecommendation();

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "HSK recommendation retrieved successfully",
	                    recommendation
	            )
	    );
	}

}
