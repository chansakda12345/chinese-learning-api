package com.sakda.chineselearning.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sakda.chineselearning.dto.TelegramMessageRequest;
import com.sakda.chineselearning.service.TelegramService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService{
	
	@Value("${telegram.bot-token}")
	private String botToken;
	
	private final WebClient webClient;
	
	@Override
	public void sendMessage(String chatId, String message) {
		
		TelegramMessageRequest request = new TelegramMessageRequest(chatId, message);
		
		webClient.post()
			.uri("https://api.telegram.org/bot" + botToken + "/sendMessage")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(String.class)
			.blockOptional();
		
	}

}
