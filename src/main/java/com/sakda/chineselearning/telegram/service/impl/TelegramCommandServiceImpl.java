package com.sakda.chineselearning.telegram.service.impl;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.telegram.service.TelegramCommandService;
import com.sakda.chineselearning.telegram.service.TelegramInformationService;
import com.sakda.chineselearning.telegram.service.TelegramLinkService;
import com.sakda.chineselearning.telegram.service.TelegramQuizService;
import com.sakda.chineselearning.telegram.service.TelegramReviewService;
import com.sakda.chineselearning.telegram.service.TelegramTeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramCommandServiceImpl implements TelegramCommandService {
	
	private final TelegramQuizService telegramQuizService;
	private final TelegramReviewService telegramReviewService;
	private final TelegramTeacherService telegramTeacherService;
	private final TelegramInformationService telegramInformationService;
	private final TelegramLinkService telegramLinkService;
	
	@Override
	public void handleCommand(String chatId, String command) {
		
		String normalizedCommand = command.trim();
		
		if (normalizedCommand.equalsIgnoreCase("A")
		        || normalizedCommand.equalsIgnoreCase("B")
		        || normalizedCommand.equalsIgnoreCase("C")
		        || normalizedCommand.equalsIgnoreCase("D")) {

		    telegramQuizService.checkAnswer(chatId, normalizedCommand);
		    return;
		}
		
		if (normalizedCommand.toLowerCase().startsWith("/link ")) {
			
			String code = normalizedCommand.substring(6).trim();
			
			telegramLinkService.sendLinkResultMessage(chatId, code);

		    return;
		}
		
		switch (normalizedCommand) {

		case "/start":
			telegramInformationService.sendStartMessage(chatId);
		    break;

		case "/help":
			telegramInformationService.sendHelpMessage(chatId);
		    break;
			
		case "/word":
			telegramTeacherService.teachRandomWord(chatId);
			break;
			
		case "/sentence":
			telegramTeacherService.teachRandomSentence(chatId);
			break;
			
		case "/review":
			telegramReviewService.sendReviewMessage(chatId);
			break;
			
		case "/quiz":
			telegramQuizService.sendRandomQuiz(chatId);
			break;
			
		case "/hsk1quiz":
		    telegramQuizService.sendHskQuiz(chatId, HskLevel.HSK1);
		    break;

		case "/hsk2quiz":
		    telegramQuizService.sendHskQuiz(chatId, HskLevel.HSK2);
		    break;

		case "/hsk3quiz":
		    telegramQuizService.sendHskQuiz(chatId, HskLevel.HSK3);
		    break;

		default:
			telegramInformationService.sendUnknownCommandMessage(chatId);;
			break;
		}
	}
}
