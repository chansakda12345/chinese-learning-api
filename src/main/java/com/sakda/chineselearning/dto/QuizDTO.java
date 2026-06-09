package com.sakda.chineselearning.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuizDTO {
	
	private Long lessonId;
	
	private String title;
	
	private List<QuestionStudentDTO> questions;

}
