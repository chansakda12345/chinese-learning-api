package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.AchievementCategory;
import com.sakda.chineselearning.enums.AchievementCode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AchievementRequestDTO {

	private String name;
	private String description;
	private String badgeIcon;
	private AchievementCategory category;
    private AchievementCode code;
	private Integer targetProgress;
	private Integer points;
	private Boolean active;
}