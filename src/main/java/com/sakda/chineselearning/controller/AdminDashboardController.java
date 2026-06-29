package com.sakda.chineselearning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.AdminDashboardDTO;
import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.service.AdminDashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Dashboard API", description = "Here for Admin to check")
@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @Operation(summary = "Get admin dashboard")
    @GetMapping
    public ResponseEntity<ApiResponse<AdminDashboardDTO>> getDashboard() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin dashboard retrieved successfully",
                        adminDashboardService.getDashboard()
                )
        );
    }
}