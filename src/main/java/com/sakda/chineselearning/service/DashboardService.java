package com.sakda.chineselearning.service;

import com.sakda.chineselearning.dto.LearningAnalyticsDTO;
import com.sakda.chineselearning.dto.ReviewStatsDTO;

public interface DashboardService {
	
	ReviewStatsDTO getReviewStats();
	
	LearningAnalyticsDTO getAnalytics();
}
