package com.sakda.chineselearning.telegram.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.telegram.entity.TelegramPost;
import com.sakda.chineselearning.telegram.enums.TelegramPostStatus;

public interface TelegramPostRepository extends JpaRepository<TelegramPost, Long>{
	
	List<TelegramPost> findByStatusAndScheduledAtLessThanEqual(
	        TelegramPostStatus status, 
	        LocalDateTime dateTime
	);
}
