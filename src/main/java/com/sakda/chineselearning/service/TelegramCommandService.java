package com.sakda.chineselearning.service;

public interface TelegramCommandService {
	
	void handleCommand(String chatId, String command);

}
