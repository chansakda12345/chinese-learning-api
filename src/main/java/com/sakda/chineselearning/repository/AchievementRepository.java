package com.sakda.chineselearning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Achievement;
import com.sakda.chineselearning.enums.AchievementCategory;
import com.sakda.chineselearning.enums.AchievementCode;

public interface AchievementRepository
        extends JpaRepository<Achievement, Long> {
    
    Optional<Achievement> findByCategoryAndTargetProgress(
            AchievementCategory category,
            Integer targetProgress
    );
    
    Optional<Achievement> findByCode(AchievementCode code);

}