package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Achievement;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.UserAchievement;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long>{
	
	boolean existsByUserAndAchievement(User user, Achievement achievement);
	
	List<UserAchievement> findByUser(User user);
	
	Long countByUser(User user);

}
