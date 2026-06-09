package com.sakda.chineselearning.service;

import com.sakda.chineselearning.dto.QuizResultDTO;
import com.sakda.chineselearning.dto.QuizSubmitDTO;

public interface QuizService {
	
	QuizResultDTO submitQuiz(QuizSubmitDTO quizSubmitDTO);

}
