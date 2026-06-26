package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserAchievementDTO {

    private Long id;
    private String name;
    private String description;
    private String badgeIcon;
    private Integer points;
    private Boolean earned;
    private LocalDateTime earnedAt;
}