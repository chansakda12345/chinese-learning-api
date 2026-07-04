package com.sakda.chineselearning.telegram.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.telegram.message.PremiumMessageBuilder;
import com.sakda.chineselearning.telegram.service.TelegramMessageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionSweeperScheduler {
	
	private final UserRepository userRepository;
	private final TelegramMessageService telegramMessageService;
	private final PremiumMessageBuilder premiumMessageBuilder;
	
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void downgradeExpiredSubscriptions() {
		log.info("Starting midnight subscription sweep...");
		
		int updatedCount = userRepository.downgradeExpiredUser(LocalDateTime.now());
		
		log.info("Subscription sweep complete. Downgraded {} expired users.", updatedCount);
	}
	
	@Scheduled(cron = "0 0 10 * * *")
	@Transactional
	public void sendExpirationReminders() {
		log.info("Starting morning 3-day expiration reminder sweep...");
		
		LocalDateTime startOfTargetDay = LocalDateTime.now().plusDays(3).truncatedTo(ChronoUnit.DAYS);
		LocalDateTime endOfTargetDay = startOfTargetDay.plusDays(1);
		
		List<User> expiringUsers = userRepository.findUsersExpiringBetween(startOfTargetDay, endOfTargetDay);
		
		for (User user : expiringUsers) {
			if (Boolean.TRUE.equals(user.getTelegramVerified()) && user.getTelegramChatId() != null) {
				
				String reminderMessage = premiumMessageBuilder.buildPremiumExpiringSoonMessage(user.getUsername());
				
				try {
					telegramMessageService.sendMessage(user.getTelegramChatId(), reminderMessage);
					log.info("Sent 3-day expiration reminder to {}", user.getEmail());
				} catch (Exception e) {
					log.error("Failed to send reminder to {}: {}", user.getEmail(), e.getMessage());
				}
			}
		}
		log.info("Reminder sweep complete. Sent {} reminders.", expiringUsers.size());
	}

}
