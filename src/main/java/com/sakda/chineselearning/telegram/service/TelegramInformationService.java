package com.sakda.chineselearning.telegram.service;

public interface TelegramInformationService {
	
	void sendStartMessage(String chatId);

    void sendHelpMessage(String chatId);
    
    void sendUnknownCommandMessage(String chatId);
}
