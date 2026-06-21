package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class ExamResultDTO {
	
	private String examTitle;
	
	private Integer score;
	
	private Integer totalQuestions;
	
	private Integer correctAnswers;
	
	private Boolean passed;
	
	private Double percentage;

}
