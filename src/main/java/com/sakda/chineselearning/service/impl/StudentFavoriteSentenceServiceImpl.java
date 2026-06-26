package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.StudentFavoriteSentenceDTO;
import com.sakda.chineselearning.entity.LearningReminder;
import com.sakda.chineselearning.entity.Sentence;
import com.sakda.chineselearning.entity.StudentFavoriteSentence;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.enums.ReminderType;
import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.StudentFavoriteSentenceMapper;
import com.sakda.chineselearning.repository.LearningReminderRepository;
import com.sakda.chineselearning.repository.SentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.AchievementService;
import com.sakda.chineselearning.service.StudentFavoriteSentenceService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentFavoriteSentenceServiceImpl implements StudentFavoriteSentenceService{
	
	private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;
	private final SentenceRepository sentenceRepository;
	private final UserRepository userRepository;
	private final StudentFavoriteSentenceMapper studentFavoriteSentenceMapper;
	private final LearningReminderRepository learningReminderRepository;
	private final AchievementService achievementService;
	
	@Override
	@Transactional
	public void saveFavoriteSentence(Long sentenceId) {
		
		User user = getCurrentUser();
		
		Sentence sentence = sentenceRepository.findById(sentenceId)
			.orElseThrow(() -> new ResourceNotFoundException("Sentence not found by id: " + sentenceId));
		
		if (studentFavoriteSentenceRepository.existsByUserAndSentence(user, sentence)) {
			return;
		}
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nextReviewAt = now.plusDays(1);
		
		StudentFavoriteSentence favoriteSentence = new StudentFavoriteSentence();
		
		favoriteSentence.setUser(user);
		favoriteSentence.setSentence(sentence);
		favoriteSentence.setSavedAt(now);
		favoriteSentence.setReviewCount(0);
		favoriteSentence.setNextReviewAt(nextReviewAt);
		
		studentFavoriteSentenceRepository.save(favoriteSentence);
		
		achievementService.checkSentenceAchievements(user);
		
		createSentenceReminder(user, sentence, nextReviewAt);
		
	}
	
	@Override
	public List<StudentFavoriteSentenceDTO> getMyFavoriteSentences() {

	    User user = getCurrentUser();

	    return studentFavoriteSentenceRepository.findByUser(user)
	            .stream()
	            .map(studentFavoriteSentenceMapper::toDTO)
	            .toList();
	}
	
	@Override
	@Transactional
	public void removeFavoriteSentence(Long favoriteId) {

	    User user = getCurrentUser();

	    StudentFavoriteSentence favoriteSentence =
	            studentFavoriteSentenceRepository.findById(favoriteId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Favorite sentence not found with id: " + favoriteId));

	    if (!favoriteSentence.getUser().getId().equals(user.getId())) {
	        throw new BusinessException(
	                "Favorite sentence does not belong to current user");
	    }
	    
	    learningReminderRepository.deleteByUserAndSentenceAndTypeAndSentFalse(
	            user,
	            favoriteSentence.getSentence(),
	            ReminderType.SENTENCE
	    );

	    studentFavoriteSentenceRepository.delete(favoriteSentence);
	}
	
	@Override
	@Transactional
	public void reviewFavoriteSentence(Long favoriteId) {
		
		User user = getCurrentUser();
		
		StudentFavoriteSentence favoriteSentence = studentFavoriteSentenceRepository.findById(favoriteId)
			.orElseThrow(() -> new ResourceNotFoundException("Favorite sentence not found with id: " + favoriteId));
		
		if (!favoriteSentence.getUser().getId().equals(user.getId())) {
	        throw new BusinessException(
	                "Favorite sentence does not belong to current user");
	    }
		
		Integer currentCount = favoriteSentence.getReviewCount();

		int newReviewCount =
		        currentCount == null
		        ? 1
		        : currentCount + 1;
		
		LocalDateTime now = LocalDateTime.now();
		
		LocalDateTime nextReviewAt = calculateNextReviewAt(now, newReviewCount);
		
		favoriteSentence.setReviewCount(newReviewCount);
		favoriteSentence.setLastReviewedAt(now);
		favoriteSentence.setNextReviewAt(nextReviewAt);
		
		studentFavoriteSentenceRepository.save(favoriteSentence);
		
		achievementService.checkReviewAchievements(user);
		
		learningReminderRepository.deleteByUserAndSentenceAndTypeAndSentFalse(
		        user,
		        favoriteSentence.getSentence(),
		        ReminderType.SENTENCE
		);

		createSentenceReminder(
		        user,
		        favoriteSentence.getSentence(),
		        nextReviewAt
		);
	}
	
	@Override
	public List<StudentFavoriteSentenceDTO> getReviewDueSentences() {

	    User user = getCurrentUser();

	    return studentFavoriteSentenceRepository
	            .findByUserAndNextReviewAtLessThanEqual(
	                    user,
	                    LocalDateTime.now()
	            )
	            .stream()
	            .map(studentFavoriteSentenceMapper::toDTO)
	            .toList();
	}
	
	private User getCurrentUser() {
	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User not found"));
	}
	
	private void createSentenceReminder(User user, Sentence sentence, LocalDateTime remindAt) {
		
		LearningReminder reminder = new LearningReminder();
		
		reminder.setUser(user);
		reminder.setSentence(sentence);
		reminder.setType(ReminderType.SENTENCE);
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
