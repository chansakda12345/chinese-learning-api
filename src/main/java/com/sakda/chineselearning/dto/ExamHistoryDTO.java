package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExamHistoryDTO {
	
	private Long attemptId;
	
	private Long examId;
	
	private String examTitle;
	
	private LocalDateTime startedAt;
	
	private LocalDateTime submittedAt;
	
	private Integer score;
	
	private Boolean passed;

}
