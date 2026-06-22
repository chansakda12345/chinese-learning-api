package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.entity.StudentFavoriteSentence;
import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.SentenceService;
import com.sakda.chineselearning.service.TelegramCommandService;
import com.sakda.chineselearning.service.TelegramQuizService;
import com.sakda.chineselearning.service.TelegramService;
import com.sakda.chineselearning.service.WordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramCommandServiceImpl implements TelegramCommandService {
	
	private final TelegramService telegramService;
	private final WordService wordService;
	private final SentenceService sentenceService;
	
	private final UserRepository userRepository;
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;
	private final TelegramQuizService telegramQuizService;
	
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
		
		switch (normalizedCommand) {

		case "/start":
			telegramService.sendMessage(chatId, getStartMessage());
			break;

		case "/help":
			telegramService.sendMessage(chatId, getHelpMessage());
			break;
			
		case "/word":
			telegramService.sendMessage(chatId, getWordMessage());
			break;
			
		case "/sentence":
			telegramService.sendMessage(chatId, getSentenceMessage());
			break;
			
		case "/review":
			telegramService.sendMessage(chatId, getReviewMessage(chatId));
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
			telegramService.sendMessage(chatId, "Unknown command. Type /help");
			break;
		}
	}
	
	private String getStartMessage() {
		return """
                Welcome to Learn Chinese!

                Available commands:

                /word
                /sentence
                /review
				/quiz
				/hsk1quiz
				/hsk2quiz
				/hsk3quiz
                /help
                """;
	}
	
	private String getHelpMessage() {
        return """
                Available Commands:

                /word
                Get random word

                /sentence
                Get random sentence

                /review
				Get review due items

				/quiz
				Get random quiz question

				/hsk1quiz
				Get random HSK1 quiz

				/hsk2quiz
				Get random HSK2 quiz

				/hsk3quiz
				Get random HSK3 quiz

                /help
                Show commands
                """;
    }
	
	private String getWordMessage() {
		
		WordDTO word = wordService.getRandomWord();
		
		return """
	            Chinese:
	            %s

	            Pinyin:
	            %s

	            English:
	            %s
	            """.formatted(
	                    word.getChinese(),
	                    word.getPinyin(),
	                    word.getEnglish()
	            );
		
	}
	
	private String getSentenceMessage() {
		
		SentenceDTO sentence = sentenceService.getRandomSentence();
		
		return   """
	            Chinese:
	            %s

	            Pinyin:
	            %s

	            English:
	            %s
	            """.formatted(
	                    sentence.getChinese(),
	                    sentence.getPinyin(),
	                    sentence.getEnglish()
	    );
	}
	
	private String getReviewMessage(String chatId) {
		
		User user = userRepository.findByTelegramChatId(chatId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		List<StudentFavoriteWord> dueWords = studentFavoriteWordRepository.findByUserAndNextReviewAtLessThanEqual(user, LocalDateTime.now());
		
		List<StudentFavoriteSentence> dueSentences = studentFavoriteSentenceRepository.findByUserAndNextReviewAtLessThanEqual(user, LocalDateTime.now());
		
		if (dueWords.isEmpty() && dueSentences.isEmpty()) {
			return """
		            🎉 Great job!

		            No items due for review.
		            """;
		}
		
		StringBuilder message = new StringBuilder("📚 Review Due\n\n");
		
		if (!dueWords.isEmpty()) {
			
			message.append("Words:\n");
			
			dueWords.forEach(word -> 
				message.append("• ")
					.append(word.getWord().getChinese())
					.append("\n")
					);
			
			message.append("\n");
		}
		
		if (!dueSentences.isEmpty()) {

		    message.append("Sentences:\n");

		    dueSentences.forEach(sentence ->
		            message.append("• ")
		                   .append(sentence.getSentence().getChinese())
		                   .append("\n"));
		}
		
		return message.toString();
	}
}
