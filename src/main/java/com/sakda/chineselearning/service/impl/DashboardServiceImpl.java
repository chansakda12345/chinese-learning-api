package com.sakda.chineselearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.ReviewStatsDTO;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
	
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
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
	
	private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
}
