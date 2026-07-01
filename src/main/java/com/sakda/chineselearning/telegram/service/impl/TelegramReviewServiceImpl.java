package com.sakda.chineselearning.telegram.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.StudentFavoriteSentence;
import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.TelegramMessageService;
import com.sakda.chineselearning.telegram.message.ReviewMessageBuilder;
import com.sakda.chineselearning.telegram.service.TelegramReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramReviewServiceImpl implements TelegramReviewService {
	
	private final UserRepository userRepository;
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;
	private final TelegramMessageService telegramMessageService;
	private final ReviewMessageBuilder reviewMessageBuilder;
	
	@Override
	public void sendReviewMessage(String chatId) {

	    User user = userRepository.findByTelegramChatId(chatId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    List<StudentFavoriteWord> dueWords =
	            studentFavoriteWordRepository.findByUserAndNextReviewAtLessThanEqual(
	                    user, LocalDateTime.now()
	            );

	    List<StudentFavoriteSentence> dueSentences =
	            studentFavoriteSentenceRepository.findByUserAndNextReviewAtLessThanEqual(
	                    user, LocalDateTime.now()
	            );

	    String message = reviewMessageBuilder.buildReviewMessage(dueWords, dueSentences);

	    telegramMessageService.sendMessage(chatId, message);
	}
}
