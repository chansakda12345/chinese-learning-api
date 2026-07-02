package com.sakda.chineselearning.telegram.service;

import com.sakda.chineselearning.telegram.dto.TelegramLinkRequestDTO;
import com.sakda.chineselearning.telegram.dto.TelegramLinkResponseDTO;

public interface TelegramLinkService {
	
	String generateVerificationCode();
	
	TelegramLinkResponseDTO verifyTelegramLink(TelegramLinkRequestDTO request);
	
	TelegramLinkResponseDTO verifyTelegramLink(String code, String chatId);
	
	void sendLinkResultMessage(String chatId, String code);

}
