package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.dto.DashboardDTO;
import com.sakda.chineselearning.dto.ProgressDTO;
import com.sakda.chineselearning.service.StudentProgressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class StudentProgressController {
	
	private final StudentProgressService studentProgressService;
	
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<List<ProgressDTO>>> getMyProgress() {
		List<ProgressDTO> progresses = studentProgressService.getMyProgress();
		
		return ResponseEntity.ok(
				new ApiResponse<>(
						true,
						"Progress retrieved sucessfully",
						progresses
						)
				);
	}
	
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

}
