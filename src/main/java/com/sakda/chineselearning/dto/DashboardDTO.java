package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class DashboardDTO {
	
	private Integer completedLessons;
	
	private Double averageScore;
	
	private Integer totalAttempts;

}
