package com.sakda.chineselearning.service.impl;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.AdminDashboardDTO;
import com.sakda.chineselearning.repository.AchievementRepository;
import com.sakda.chineselearning.repository.LessonRepository;
import com.sakda.chineselearning.repository.MockExamRepository;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.repository.SentenceRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.repository.WordRepository;
import com.sakda.chineselearning.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService{
	
	private final UserRepository userRepository;

	private final LessonRepository lessonRepository;

	private final WordRepository wordRepository;

	private final SentenceRepository sentenceRepository;

	private final QuestionRepository questionRepository;

	private final MockExamRepository mockExamRepository;

	private final AchievementRepository achievementRepository;
	
	@Override
	public AdminDashboardDTO getDashboard() {

	    AdminDashboardDTO dto = new AdminDashboardDTO();

	    dto.setTotalUsers(userRepository.count());
	    dto.setTotalLessons(lessonRepository.count());
	    dto.setTotalWords(wordRepository.count());
	    dto.setTotalSentences(sentenceRepository.count());
	    dto.setTotalQuestions(questionRepository.count());
	    dto.setTotalMockExams(mockExamRepository.count());
	    dto.setTotalAchievements(achievementRepository.count());

	    dto.setActiveStudents(0L);
	    dto.setNewUsersToday(0L);

	    return dto;
	}

}
