package com.sakda.chineselearning.service;

import com.sakda.chineselearning.enums.HskLevel;

public interface TelegramQuizService {
	
	void sendRandomQuiz(String chatId);
	
	void sendHskQuiz(String chatId, HskLevel level);
	
	void checkAnswer(String chatId, String answer);

}
