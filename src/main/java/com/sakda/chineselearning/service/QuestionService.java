package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.QuestionDTO;

public interface QuestionService {
		
	List<QuestionDTO> getQuestions();
	
	QuestionDTO getQuestionById(Long id);
	
	QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO);
	
	void deleteQuestion(Long id);
	
	QuestionDTO createQuestionForLesson(
	        Long lessonId,
	        QuestionDTO questionDTO
	);

}
