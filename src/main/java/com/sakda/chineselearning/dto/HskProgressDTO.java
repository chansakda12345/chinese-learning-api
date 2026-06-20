package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.HskLevel;

import lombok.Data;

@Data
public class HskProgressDTO {
	
	private HskLevel level;
	
	private long totalWords;
	
	private long reviewedWords;
	
	private double completionPercentage;

}
