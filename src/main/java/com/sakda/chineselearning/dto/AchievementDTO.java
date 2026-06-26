package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.AchievementCategory;
import com.sakda.chineselearning.enums.AchievementCode;

import lombok.Data;

@Data
public class AchievementDTO {

    private Long id;
    private String name;
    private String description;
    private String badgeIcon;
    private AchievementCode code;
    private AchievementCategory category;
    private Integer targetProgress;
    private Integer points;
    private Boolean active;
}