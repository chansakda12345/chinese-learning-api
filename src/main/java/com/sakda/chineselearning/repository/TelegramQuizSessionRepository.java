package com.sakda.chineselearning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.TelegramQuizSession;
import com.sakda.chineselearning.entity.User;

public interface TelegramQuizSessionRepository extends JpaRepository<TelegramQuizSession, Long> {
	
	Optional<TelegramQuizSession> findFirstByUserAndAnsweredFalseOrderByCreatedAtDesc(User user);

}
