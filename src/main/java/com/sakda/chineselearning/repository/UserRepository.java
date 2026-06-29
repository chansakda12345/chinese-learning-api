package com.sakda.chineselearning.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	Optional<User> findByTelegramChatId(String telegramChatId);
	
	Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
	        String username,
	        String email,
	        Pageable pageable
	);
}
