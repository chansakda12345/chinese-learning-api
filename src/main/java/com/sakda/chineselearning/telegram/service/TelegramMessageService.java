package com.sakda.chineselearning.telegram.service;

public interface TelegramMessageService {
	
	void sendMessage(String chatId, String message);
	
	void sendToUser(String chatId, String message);
    
    void sendToChannel(String channelId, String message);
    
    void sendToGroup(String groupId, String message);

}
