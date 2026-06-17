package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.StudentFavoriteWordDTO;
import com.sakda.chineselearning.entity.LearningReminder;
import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.Word;
import com.sakda.chineselearning.enums.ReminderType;
import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.StudentFavoriteWordMapper;
import com.sakda.chineselearning.repository.LearningReminderRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.repository.WordRepository;
import com.sakda.chineselearning.service.StudentFavoriteWordService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentFavoriteWordServiceImpl implements StudentFavoriteWordService {

	
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final WordRepository wordRepository;
	private final UserRepository userRepository;
	private final StudentFavoriteWordMapper studentFavoriteWordMapper;
	private final LearningReminderRepository learningReminderRepository;
	
	@Transactional
	@Override
	public void saveFavoriteWord(Long wordId) {
		
		User user = getCurrentUser();
		
		Word word = wordRepository.findById(wordId)
				.orElseThrow(() -> new ResourceNotFoundException("Word not found with id: " + wordId));
		
		if (studentFavoriteWordRepository.existsByUserAndWord(user, word)) {
			return;
		}
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nextReviewAt = now.plusDays(1);

	    StudentFavoriteWord favorite = new StudentFavoriteWord();

	    favorite.setUser(user);
	    favorite.setWord(word);
	    favorite.setSavedAt(now);
	    favorite.setReviewCount(0);
	    favorite.setNextReviewAt(nextReviewAt);

	    studentFavoriteWordRepository.save(favorite);

	    createWordReminder(user, word, nextReviewAt);		
	}

	@Override
	public List<StudentFavoriteWordDTO> getMyFavoriteWords() {
		
		User user = getCurrentUser();
		
		return studentFavoriteWordRepository.findByUser(user)
				.stream()
				.map(studentFavoriteWordMapper::toDTO)
				.toList();
	}
	
	@Transactional
	@Override
	public void removeFavoriteWord(Long favoriteId) {
		
		User user = getCurrentUser();
		
		StudentFavoriteWord favoriteWord = studentFavoriteWordRepository.findById(favoriteId)
			.orElseThrow(() -> new ResourceNotFoundException("Favorite word not found with id: " + favoriteId));
		
		if(!favoriteWord.getUser().getId().equals(user.getId())) {
			throw new BusinessException("Favorite word does not belong to current user");
		}
		

		learningReminderRepository.deleteByUserAndWordAndTypeAndSentFalse(user, favoriteWord.getWord(), ReminderType.WORD);
		
		studentFavoriteWordRepository.delete(favoriteWord);
	}
	
	@Transactional
	@Override
	public void reviewFavoriteWord(Long favoriteId) {

	    User user = getCurrentUser();

	    StudentFavoriteWord favoriteWord = studentFavoriteWordRepository.findById(favoriteId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Favorite word not found with id: " + favoriteId));

	    if (!favoriteWord.getUser().getId().equals(user.getId())) {
	        throw new BusinessException("Favorite word does not belong to current user");
	    }

	    Integer currentCount = favoriteWord.getReviewCount();

	    int newReviewCount = 
	    		currentCount == null 
	    		? 1 
	    		: currentCount + 1;
	    
	    LocalDateTime now = LocalDateTime.now();
	    
	    LocalDateTime nextReviewAt = calculateNextReviewAt(now, newReviewCount);

	    favoriteWord.setReviewCount(newReviewCount);
	    favoriteWord.setLastReviewedAt(now);
	    favoriteWord.setNextReviewAt(nextReviewAt);

	    studentFavoriteWordRepository.save(favoriteWord);
	    
	    learningReminderRepository.deleteByUserAndWordAndTypeAndSentFalse(user, favoriteWord.getWord(), ReminderType.WORD);
	    
	    createWordReminder(user, favoriteWord.getWord(), nextReviewAt);
	}
	
	private User getCurrentUser() {
	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User not found"));
	}
	
	private void createWordReminder(
			User user,
			Word word,
			LocalDateTime remindAt
			) {
		
		LearningReminder reminder = new LearningReminder();
		
		reminder.setUser(user);
		reminder.setWord(word);
		reminder.setType(ReminderType.WORD);
		reminder.setSent(false);
		reminder.setCreatedAt(LocalDateTime.now());
		reminder.setRemindAt(remindAt);
		
		learningReminderRepository.save(reminder);
	}
	
	private LocalDateTime calculateNextReviewAt(LocalDateTime now, int reviewCount) {

	    if (reviewCount == 1) {
	        return now.plusDays(1);
	    }

	    if (reviewCount == 2) {
	        return now.plusDays(3);
	    }

	    if (reviewCount == 3) {
	        return now.plusDays(7);
	    }

	    return now.plusDays(14);
	}


}
