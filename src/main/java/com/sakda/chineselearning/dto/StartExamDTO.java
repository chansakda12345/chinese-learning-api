package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class StartExamDTO {
	
	private Long attemptId;
	
	private Long examId;
	
	private String examTitle;
	
	private Integer durationMinutes;

}
