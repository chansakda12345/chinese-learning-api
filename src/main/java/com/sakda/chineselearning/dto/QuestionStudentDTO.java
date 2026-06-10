package com.sakda.chineselearning.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionStudentDTO {
	
	private Long id;
	
	private String questionText;
	
	private List<String> options;

}
