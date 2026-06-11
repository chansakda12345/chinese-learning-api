package com.sakda.chineselearning.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.DashboardDTO;
import com.sakda.chineselearning.dto.ProgressDTO;
import com.sakda.chineselearning.entity.StudentProgress;
import com.sakda.chineselearning.mapper.StudentProgressMapper;
import com.sakda.chineselearning.repository.StudentProgressRepository;
import com.sakda.chineselearning.service.StudentProgressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentProgressServiceImpl implements StudentProgressService {
	
	private final StudentProgressRepository studentProgressRepository;
	
	private final StudentProgressMapper studentProgressMapper;

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

}
