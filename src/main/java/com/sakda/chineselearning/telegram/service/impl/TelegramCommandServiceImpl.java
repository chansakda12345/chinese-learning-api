package com.sakda.chineselearning.telegram.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.telegram.message.PremiumMessageBuilder;
import com.sakda.chineselearning.telegram.service.TelegramCommandService;
import com.sakda.chineselearning.telegram.service.TelegramInformationService;
import com.sakda.chineselearning.telegram.service.TelegramLinkService;
import com.sakda.chineselearning.telegram.service.TelegramMessageService;
import com.sakda.chineselearning.telegram.service.TelegramQuizService;
import com.sakda.chineselearning.telegram.service.TelegramReviewService;
import com.sakda.chineselearning.telegram.service.TelegramTeacherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramCommandServiceImpl implements TelegramCommandService {
	
	private final TelegramQuizService telegramQuizService;
	private final TelegramReviewService telegramReviewService;
	private final TelegramTeacherService telegramTeacherService;
	private final TelegramInformationService telegramInformationService;
	private final TelegramLinkService telegramLinkService;
	private final UserRepository userRepository;
	private final TelegramMessageService telegramMessageService;
	private final PremiumMessageBuilder premiumMessageBuilder;
	
	@Override
	public void handleCommand(String chatId, String command) {
		
		if (chatId.startsWith("-")) {
			log.info("Silently ignoring command '{}' from group/channel: {}", command, chatId);
			return;
		}
		
		String normalizedCommand = command.trim();
		
		// 1. PUBLIC: /link command
		if (normalizedCommand.toLowerCase().startsWith("/link ")) {
			String code = normalizedCommand.substring(6).trim();
			telegramLinkService.sendLinkResultMessage(chatId, code);
			return;
		}
		
		// 2. PUBLIC: /start command
		if (normalizedCommand.equalsIgnoreCase("/start")) {
			telegramInformationService.sendStartMessage(chatId);
			return;
		}
		
		// 3. PUBLIC: /help command
		if (normalizedCommand.equalsIgnoreCase("/help")) {
			telegramInformationService.sendHelpMessage(chatId);
			return;
		}
		
		// 4. GUARD: Verify Premium Access for all other commands
		if (!checkPremiumAccess(chatId)) {
			return;
		}
		
		// 5. PREMIUM: Handle quiz answers (A, B, C, D)
		if (normalizedCommand.equalsIgnoreCase("A")
		        || normalizedCommand.equalsIgnoreCase("B")
		        || normalizedCommand.equalsIgnoreCase("C")
		        || normalizedCommand.equalsIgnoreCase("D")) {

		    telegramQuizService.checkAnswer(chatId, normalizedCommand);
		    return;
		}
		
		// 6. PREMIUM: Switch on the rest of the commands
		switch (normalizedCommand) {
			
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
			telegramInformationService.sendUnknownCommandMessage(chatId);
			break;
		}
	}
	
	private boolean checkPremiumAccess(String chatId) {
		
		User user = userRepository.findByTelegramChatId(chatId).orElse(null);
		
		if (user == null || !Boolean.TRUE.equals(user.getTelegramVerified())) {
			log.warn("Blocked request from unlinked chatId: {}", chatId);
			telegramMessageService.sendMessage(
					chatId, 
					premiumMessageBuilder.buildAccountNotLinkedMessage()
			);
			return false;
		}
		
		boolean hasPremium = Boolean.TRUE.equals(user.getIsPremium());
		boolean notExpired = user.getPremiumExpiresAt() == null || user.getPremiumExpiresAt().isAfter(LocalDateTime.now());
		
		if (!hasPremium || !notExpired) {
			log.warn("Blocked request from non-premium user email: {}", user.getEmail());
			
			telegramMessageService.sendMessage(
					chatId, 
					premiumMessageBuilder.buildPremiumOnlyMessage()
			);
			return false;
		}
		
		return true;
	}
}
