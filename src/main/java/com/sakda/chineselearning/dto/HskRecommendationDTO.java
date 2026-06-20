package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.HskLevel;

import lombok.Data;

@Data
public class HskRecommendationDTO {
	
	private HskLevel currentLevel;
	
	private double completion;
	
	private HskLevel nextLevel;
	
	private String recommendation;

}
