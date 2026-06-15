package com.sakda.chineselearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.LearningReminder;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.Word;
import com.sakda.chineselearning.enums.ReminderType;

public interface LearningReminderRepository extends JpaRepository<LearningReminder, Long>{
	
	void deleteByUserAndWordAndTypeAndSentFalse(
	        User user,
	        Word word,
	        ReminderType type
	);
	
	long countByUserAndWordAndTypeAndSentFalse(
	        User user,
	        Word word,
	        ReminderType type
	);

}
