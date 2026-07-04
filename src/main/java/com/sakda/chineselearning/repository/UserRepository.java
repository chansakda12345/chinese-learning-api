package com.sakda.chineselearning.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	@Modifying
	@Query("UPDATE User u SET u.isPremium = false WHERE u.isPremium = true AND u.premiumExpiresAt < :now")
	int downgradeExpiredUser(@Param("now") LocalDateTime now);
	
	@Query("SELECT u FROM User u WHERE u.isPremium = true AND u.premiumExpiresAt >= :startDate AND u.premiumExpiresAt < :endDate")
	List<User> findUsersExpiringBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	
}
