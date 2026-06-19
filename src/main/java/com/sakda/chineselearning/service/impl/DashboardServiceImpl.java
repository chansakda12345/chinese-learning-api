package com.sakda.chineselearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.LearningAnalyticsDTO;
import com.sakda.chineselearning.dto.ReviewStatsDTO;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
	
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;
	private final UserRepository userRepository;
	
	@Override
	public ReviewStatsDTO getReviewStats() {
		
		User user = getCurrentUser();
		
		LocalDate today = LocalDate.now();
		
		LocalDateTime startOfDay = today.atStartOfDay();
		LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
		
		long totalFavoriteWords = studentFavoriteWordRepository.countByUser(user);
		
		long reviewedToday = studentFavoriteWordRepository.countByUserAndLastReviewedAtBetween(user, startOfDay, endOfDay);
		
		long dueForReview = studentFavoriteWordRepository.countByUserAndNextReviewAtLessThanEqual(user, LocalDateTime.now());

		long neverReviewed = studentFavoriteWordRepository.countByUserAndLastReviewedAtIsNull(user);
		
		ReviewStatsDTO dto = new ReviewStatsDTO();
		
		dto.setTotalFavoriteWords(totalFavoriteWords);
		dto.setNeverReviewed(neverReviewed);
		dto.setDueForReview(dueForReview);
		dto.setReviewedToday(reviewedToday);
		
		return dto;
	}
	
	@Override
	public LearningAnalyticsDTO getAnalytics() {
		
	    User user = getCurrentUser();

	    LocalDate today = LocalDate.now();

	    LocalDateTime startOfDay = today.atStartOfDay();
	    LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

	    LocalDateTime startOfWeek = today.minusDays(6).atStartOfDay();
	    LocalDateTime endOfWeek = endOfDay;

	    LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
	    LocalDateTime endOfMonth = endOfDay;

	    LocalDateTime now = LocalDateTime.now();

	    long totalFavoriteWords =
	            studentFavoriteWordRepository.countByUser(user);

	    long totalFavoriteSentences =
	            studentFavoriteSentenceRepository.countByUser(user);

	    long reviewedWordsToday =
	            studentFavoriteWordRepository.countByUserAndLastReviewedAtBetween(
	                    user,
	                    startOfDay,
	                    endOfDay
	            );

	    long reviewedSentencesToday =
	            studentFavoriteSentenceRepository.countByUserAndLastReviewedAtBetween(
	                    user,
	                    startOfDay,
	                    endOfDay
	            );

	    long reviewedWordsThisWeek =
	            studentFavoriteWordRepository.countByUserAndLastReviewedAtBetween(
	                    user,
	                    startOfWeek,
	                    endOfWeek
	            );

	    long reviewedSentencesThisWeek =
	            studentFavoriteSentenceRepository.countByUserAndLastReviewedAtBetween(
	                    user,
	                    startOfWeek,
	                    endOfWeek
	            );

	    long reviewedWordsThisMonth =
	            studentFavoriteWordRepository.countByUserAndLastReviewedAtBetween(
	                    user,
	                    startOfMonth,
	                    endOfMonth
	            );

	    long reviewedSentencesThisMonth =
	            studentFavoriteSentenceRepository.countByUserAndLastReviewedAtBetween(
	                    user,
	                    startOfMonth,
	                    endOfMonth
	            );

	    long dueWords =
	            studentFavoriteWordRepository.countByUserAndNextReviewAtLessThanEqual(
	                    user,
	                    now
	            );

	    long dueSentences =
	            studentFavoriteSentenceRepository.countByUserAndNextReviewAtLessThanEqual(
	                    user,
	                    now
	            );

	    long neverReviewedWords =
	            studentFavoriteWordRepository.countByUserAndLastReviewedAtIsNull(user);

	    long neverReviewedSentences =
	            studentFavoriteSentenceRepository.countByUserAndLastReviewedAtIsNull(user);
	    
	    LearningAnalyticsDTO dto = new LearningAnalyticsDTO();
	    
	    dto.setTotalFavoriteWords(totalFavoriteWords);
	    dto.setTotalFavoriteSentences(totalFavoriteSentences);
	    
	    dto.setReviewedToday(reviewedWordsToday + reviewedSentencesToday);
	    dto.setReviewedThisWeek(reviewedWordsThisWeek + reviewedSentencesThisWeek);
	    dto.setReviewedThisMonth(reviewedWordsThisMonth + reviewedSentencesThisMonth);
	    
	    dto.setDueWords(dueWords);
	    dto.setDueSentences(dueSentences);
	    
	    dto.setNeverReviewedWords(neverReviewedWords);
	    dto.setNeverReviewedSentences(neverReviewedSentences);
	    
	    dto.setCurrentStreak(calculateCurrentStreak(user));
		
		return dto;
	}
	
	private long calculateCurrentStreak(User user) {

	    Set<LocalDate> reviewedDates = new HashSet<>();

	    studentFavoriteWordRepository
	        .findByUserAndLastReviewedAtIsNotNullOrderByLastReviewedAtDesc(user)
	        .forEach(favoriteWord ->
	            reviewedDates.add(favoriteWord.getLastReviewedAt().toLocalDate())
	        );

	    studentFavoriteSentenceRepository
	        .findByUserAndLastReviewedAtIsNotNullOrderByLastReviewedAtDesc(user)
	        .forEach(favoriteSentence ->
	            reviewedDates.add(favoriteSentence.getLastReviewedAt().toLocalDate())
	        );

	    long streak = 0;

	    LocalDate currentDate = LocalDate.now();

	    while (reviewedDates.contains(currentDate)) {
	        streak++;
	        currentDate = currentDate.minusDays(1);
	    }

	    return streak;
	}
	
	private User getCurrentUser() {
	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User not found"));
	}
	
}
