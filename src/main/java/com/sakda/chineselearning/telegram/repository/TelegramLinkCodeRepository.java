package com.sakda.chineselearning.telegram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.telegram.entity.TelegramLinkCode;

public interface TelegramLinkCodeRepository extends JpaRepository<TelegramLinkCode, Long>{
	
	Optional<TelegramLinkCode> findByCode(String code);
	
	void deleteByUser(User user);
	
	Optional<TelegramLinkCode> findByUser(User user);
	
	boolean existsByCode(String code);

}
