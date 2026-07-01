package com.sakda.chineselearning.telegram.service;

public interface TelegramTeacherService {
	
	void teachRandomWord(String chatId);
	
	void teachRandomSentence(String chatId);

}
