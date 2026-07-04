package com.sakda.chineselearning.telegram.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sakda.chineselearning.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionSweeperScheduler {
	
	private final UserRepository userRepository;
	
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void downgradeExpiredSubscriptions() {
		log.info("Starting midnight subscription sweep...");
		
		int updatedCount = userRepository.downgradeExpiredUser(LocalDateTime.now());
		
		log.info("Subscription sweep complete. Downgraded {} expired users.", updatedCount);
	}

}
