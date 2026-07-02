package com.sakda.chineselearning.telegram.service;

public interface TelegramCommandService {
	
	void handleCommand(String chatId, String command);
	
}
