package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.entity.StudentFavoriteSentence;
import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.SentenceService;
import com.sakda.chineselearning.service.TelegramCommandService;
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
	
	@Override
	public void handleCommand(String chatId, String command) {
		switch (command) {

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
