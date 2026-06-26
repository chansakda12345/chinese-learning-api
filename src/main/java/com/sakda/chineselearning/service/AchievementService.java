package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.AchievementDTO;
import com.sakda.chineselearning.dto.AchievementProgressDTO;
import com.sakda.chineselearning.dto.AchievementRequestDTO;
import com.sakda.chineselearning.dto.MyAchievementDTO;
import com.sakda.chineselearning.entity.Achievement;
import com.sakda.chineselearning.entity.User;

public interface AchievementService {

    AchievementDTO createAchievement(AchievementRequestDTO dto);

    List<AchievementDTO> getAllAchievements();

    AchievementDTO updateAchievement(Long id, AchievementRequestDTO dto);
    
    List<MyAchievementDTO> getMyAchievements();
    
    List<AchievementProgressDTO> getAchievementProgress();

    void checkWordAchievements(User user);

    void checkSentenceAchievements(User user);

    void checkReviewAchievements(User user);

    void checkQuizAchievements(User user);

    void checkExamAchievements(User user);

    void awardAchievement(User user, Achievement achievement);
}