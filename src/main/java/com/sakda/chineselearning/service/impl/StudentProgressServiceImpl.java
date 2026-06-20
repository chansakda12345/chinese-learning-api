package com.sakda.chineselearning.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.DashboardDTO;
import com.sakda.chineselearning.dto.HskProgressDTO;
import com.sakda.chineselearning.dto.HskRecommendationDTO;
import com.sakda.chineselearning.dto.ProgressDTO;
import com.sakda.chineselearning.entity.StudentProgress;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.StudentProgressMapper;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.StudentProgressRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.repository.WordRepository;
import com.sakda.chineselearning.service.StudentProgressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentProgressServiceImpl implements StudentProgressService {
	
	private final StudentProgressRepository studentProgressRepository;
	private final StudentProgressMapper studentProgressMapper;
	private final WordRepository wordRepository;
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final UserRepository userRepository;

	@Override
	public List<ProgressDTO> getMyProgress() {
		
		Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
		
		String email = authentication.getName();
		
		List<StudentProgress> progresses = studentProgressRepository.findByUserEmail(email);
		
		return progresses.stream()
				.map(studentProgressMapper::toProgressDTO)
				.toList();
	}

	@Override
	public DashboardDTO getDashboard() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		List<StudentProgress> progresses = studentProgressRepository.findByUserEmail(email);
		
		int completedLessons = progresses.size();
		
		double averageScore = progresses.stream()
			.mapToInt(StudentProgress::getScore)
			.average()
			.orElse(0.0);
		
		DashboardDTO dashboardDTO = new DashboardDTO();
		
		dashboardDTO.setCompletedLessons((completedLessons));
		dashboardDTO.setAverageScore(averageScore);
		dashboardDTO.setTotalAttempts(completedLessons);
		
		return dashboardDTO;
	}
	
	@Override
	public List<HskProgressDTO> getHskProgress() {

	    User user = getCurrentUser();

	    return Arrays.stream(HskLevel.values())
	            .map(level -> {

	                long totalWords = wordRepository.countByLevel(level);

	                long reviewedWords =
	                        studentFavoriteWordRepository
	                                .countByUserAndWord_LevelAndReviewCountGreaterThan(
	                                        user,
	                                        level,
	                                        0
	                                );

	                double completionPercentage = 0;

	                if (totalWords > 0) {
	                    completionPercentage =
	                            (double) reviewedWords * 100 / totalWords;
	                }

	                HskProgressDTO dto = new HskProgressDTO();

	                dto.setLevel(level);
	                dto.setTotalWords(totalWords);
	                dto.setReviewedWords(reviewedWords);
	                dto.setCompletionPercentage(
	                        Math.round(completionPercentage * 100.0) / 100.0
	                );

	                return dto;
	            })
	            .toList();
	}

	@Override
	public HskRecommendationDTO getRecommendation() {

	    List<HskProgressDTO> progressList = getHskProgress();

	    HskRecommendationDTO dto = new HskRecommendationDTO();

	    for (int i = 0; i < progressList.size(); i++) {

	        HskProgressDTO progress = progressList.get(i);

	        if (progress.getCompletionPercentage() < 100) {

	            dto.setCurrentLevel(progress.getLevel());
	            dto.setCompletion(progress.getCompletionPercentage());

	            boolean hasNextLevel = i < progressList.size() - 1;

	            if (hasNextLevel) {
	                HskLevel nextLevel = progressList.get(i + 1).getLevel();
	                dto.setNextLevel(nextLevel);

	                if (progress.getCompletionPercentage() >= 80) {
	                    dto.setRecommendation("Start " + nextLevel);
	                } else {
	                    dto.setRecommendation("Continue " + progress.getLevel());
	                }

	            } else {
	                dto.setNextLevel(null);
	                dto.setRecommendation("Continue " + progress.getLevel());
	            }

	            return dto;
	        }
	    }

	    dto.setCurrentLevel(HskLevel.HSK6);
	    dto.setCompletion(100);
	    dto.setNextLevel(null);
	    dto.setRecommendation("Congratulations! HSK path completed.");

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
