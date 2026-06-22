package com.sakda.chineselearning.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.LeaderboardDTO;
import com.sakda.chineselearning.entity.StudentExamAttempt;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.StudentExamAttemptRepository;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.StudentProgressRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.LeaderboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

	private final UserRepository userRepository;
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;
	private final StudentProgressRepository studentProgressRepository;
	private final StudentExamAttemptRepository studentExamAttemptRepository;

	@Override
	public List<LeaderboardDTO> getLeaderboard() {
		
		List<User> users = userRepository.findAll();
		
		List<LeaderboardDTO> leaderboard = new ArrayList<>();
		
		for (User user : users) {
			
			Long totalReviews = 
					studentFavoriteSentenceRepository.sumReviewCountByUser(user)
					+ studentFavoriteWordRepository.sumReviewCountByUser(user);
			
			Integer totalQuizScore = studentProgressRepository.sumScoreByUser(user);
			
			Integer totalExamScore = calculateTotalExamScore(user);
			
			Long totalPoints = totalReviews + totalQuizScore.longValue() + totalExamScore.longValue();
			
			LeaderboardDTO dto = new LeaderboardDTO();
			
			dto.setUserId(user.getId());
			dto.setUsername(user.getUsername());
			
			dto.setTotalReviews(totalReviews);
			dto.setTotalQuizScore(totalQuizScore);
			dto.setTotalExamScore(totalExamScore);
			
			dto.setTotalPoints(totalPoints);
			dto.setStreak(0);
			
			leaderboard.add(dto);
		}
		
		leaderboard.sort(Comparator.comparing(LeaderboardDTO::getTotalPoints).reversed());
		
		for (int i =0; i < leaderboard.size(); i++) {
			
			leaderboard.get(i).setRank(i+1);
			
		}
		
		return leaderboard;
	}

	@Override
	public List<LeaderboardDTO> getTop10() {
		
		List<LeaderboardDTO> leaderboard = getLeaderboard();
		
		return leaderboard.subList(0, Math.min(10, leaderboard.size()));
	}

	@Override
	public LeaderboardDTO getCurrentUserRank() {
		
		User user = getCurrentUser();
		
		return getLeaderboard()
				.stream()
				.filter(dto -> dto.getUserId().equals(user.getId()))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Current user not found in leaderboard"));
	}
	
	private Integer calculateTotalExamScore(User user) {
		
		List<StudentExamAttempt> attempts = studentExamAttemptRepository.findByUser(user);
		
		Map<Long, Integer> bestScoreByExam = new HashMap<>();
		
		for (StudentExamAttempt attempt : attempts) {
			
			if (attempt.getMockExam() == null) {
				continue;
			}
			
			Long examId = attempt.getMockExam().getId();
			
			Integer score = attempt.getScore() == null
					? 0
					: attempt.getScore();
			
			Integer currentBest = bestScoreByExam.getOrDefault(examId, 0);
			
			if (score > currentBest) {
				bestScoreByExam.put(examId, score);
			}
			
		}
				
		return bestScoreByExam.values()
				.stream()
				.mapToInt(Integer::intValue)
				.sum();
		
	}
	
	private User getCurrentUser() {

	    Authentication authentication =
	            SecurityContextHolder
	                    .getContext()
	                    .getAuthentication();

	    String email = authentication.getName();

	    return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User not found"));
	}

}
