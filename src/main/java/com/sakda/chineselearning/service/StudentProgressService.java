package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.DashboardDTO;
import com.sakda.chineselearning.dto.HskProgressDTO;
import com.sakda.chineselearning.dto.HskRecommendationDTO;
import com.sakda.chineselearning.dto.ProgressDTO;

public interface StudentProgressService {
	
	List<ProgressDTO> getMyProgress ();
	
	DashboardDTO getDashboard();
	
	List<HskProgressDTO> getHskProgress();
	
	HskRecommendationDTO getRecommendation();

}
