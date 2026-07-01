package com.sakda.chineselearning.service;

public interface TelegramMessageService {
	
	void sendMessage(String chatId, String message);

}
