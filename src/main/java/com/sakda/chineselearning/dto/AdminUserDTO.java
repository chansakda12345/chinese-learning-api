package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import com.sakda.chineselearning.entity.Role;

import lombok.Data;

@Data
public class AdminUserDTO {

    private Long id;

    private String username;

    private String email;

    private Role role;

    private Boolean enabled;

    private Integer totalPoints;

    private Integer achievementCount;

    private Boolean telegramConnected;

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;
}