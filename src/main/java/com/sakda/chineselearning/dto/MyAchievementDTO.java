package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import com.sakda.chineselearning.enums.AchievementCategory;
import com.sakda.chineselearning.enums.AchievementCode;

import lombok.Data;

@Data
public class MyAchievementDTO {
	
	private Long id;
    private String name;
    private String description;
    private String badgeIcon;
    private AchievementCode code;
    private AchievementCategory category;
    private Integer targetProgress;
    private Integer points;
    private Boolean active;

    private Boolean earned;
    private LocalDateTime earnedAt;
}
