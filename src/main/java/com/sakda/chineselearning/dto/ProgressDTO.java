package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class ProgressDTO {
	
	private Long lessonId;
	
	private String lessonTitle;
	
	private Integer score;
	
	private Double percentage;

}
