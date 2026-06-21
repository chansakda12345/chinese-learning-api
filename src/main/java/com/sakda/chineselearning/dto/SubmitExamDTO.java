package com.sakda.chineselearning.dto;

import java.util.List;

import lombok.Data;

@Data
public class SubmitExamDTO {
	
	private Long attemptId;
	
	private List<ExamAnswerDTO> answers;

}
