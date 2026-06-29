package com.sakda.chineselearning.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sakda.chineselearning.dto.AdminStudentProgressDTO;
import com.sakda.chineselearning.dto.AdminUserDTO;
import com.sakda.chineselearning.dto.ApiResponse;
import com.sakda.chineselearning.service.AdminUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin User API", description = "Admin user management")
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Operation(summary = "Get users")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminUserDTO>>> getUsers(
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Users retrieved successfully",
                        adminUserService.getUsers(pageable)
                )
        );
    }

    @Operation(summary = "Search users")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<AdminUserDTO>>> searchUsers(
            @RequestParam String keyword,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Users searched successfully",
                        adminUserService.searchUsers(keyword, pageable)
                )
        );
    }
    
    @Operation(summary = "Enable user")
    @PutMapping("/{userId}/enable")
    public ResponseEntity<ApiResponse<AdminUserDTO>> enableUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User enabled successfully",
                        adminUserService.enableUser(userId)
                )
        );
    }

    @Operation(summary = "Disable user")
    @PutMapping("/{userId}/disable")
    public ResponseEntity<ApiResponse<AdminUserDTO>> disableUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User disabled successfully",
                        adminUserService.disableUser(userId)
                )
        );
    }
    
    @Operation(summary = "Get student progress")
    @GetMapping("/{userId}/progress")
    public ResponseEntity<ApiResponse<AdminStudentProgressDTO>> getStudentProgress(@PathVariable Long userId) {
    	return ResponseEntity.ok(new ApiResponse<>(
    			true,
    			"Student progress retrieved successfully",
    			adminUserService.getStudentProgress(userId)
    			));
    }
}