package com.sakda.chineselearning.telegram.service.impl;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.service.TelegramMessageService;
import com.sakda.chineselearning.telegram.message.InformationMessageBuilder;
import com.sakda.chineselearning.telegram.service.TelegramInformationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramInformationServiceImpl implements TelegramInformationService {
	
	private final InformationMessageBuilder informationMessageBuilder;
	private final TelegramMessageService telegramMessageService;

	@Override
	public void sendStartMessage(String chatId) {
		
		telegramMessageService.sendMessage(
		        chatId,
		        informationMessageBuilder.buildStartMessage()
		    );
	}

	@Override
	public void sendHelpMessage(String chatId) {
		
		telegramMessageService.sendMessage(
		        chatId,
		        informationMessageBuilder.buildHelpMessage()
		    );
	}

	@Override
	public void sendUnknownCommandMessage(String chatId) {
		
		telegramMessageService.sendMessage(
				chatId, 
				informationMessageBuilder.buildUnknownCommandMessage());
		
	}
}
