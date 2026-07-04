package com.sakda.chineselearning.telegram.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.telegram.enums.SubscriptionPlan;
import com.sakda.chineselearning.telegram.message.PremiumMessageBuilder;
import com.sakda.chineselearning.telegram.service.SubscriptionService;
import com.sakda.chineselearning.telegram.service.TelegramMessageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
	
	private final UserRepository userRepository;
	private final TelegramMessageService telegramMessageService;
	private final PremiumMessageBuilder premiumMessageBuilder;
	
	@Override
	@Transactional
	public void grantPremiumAccess(String email, SubscriptionPlan plan) {
		
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with emaail: " + email));
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime currentExpiry = user.getPremiumExpiresAt();
		
		if (currentExpiry == null || currentExpiry.isBefore(now)) {
			user.setPremiumExpiresAt(now.plusDays(plan.getDays()));
		} else {
			user.setPremiumExpiresAt(currentExpiry.plusDays(plan.getDays()));
		}
		
		user.setIsPremium(true);
		userRepository.save(user);
		
		log.info("Granted {} days of premium to {}. New expiry: {}", plan.getDays(), email, user.getPremiumExpiresAt());
		
		if (Boolean.TRUE.equals(user.getTelegramVerified()) && user.getTelegramChatId() != null) {
			
			String welcomeMessage = premiumMessageBuilder.buildPremiumWelcomeMessage(user.getUsername(), plan.getDays());
			
			try {
                telegramMessageService.sendMessage(user.getTelegramChatId(), welcomeMessage);
            } catch (Exception e) {
                log.error("Failed to send premium welcome message to {}: {}", email, e.getMessage());
            }
		}
	}

}
