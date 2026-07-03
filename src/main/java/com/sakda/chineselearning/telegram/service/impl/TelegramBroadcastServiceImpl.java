package com.sakda.chineselearning.telegram.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.telegram.entity.TelegramPost;
import com.sakda.chineselearning.telegram.enums.TelegramPostStatus;
import com.sakda.chineselearning.telegram.enums.TelegramTargetType;
import com.sakda.chineselearning.telegram.repository.TelegramPostRepository;
import com.sakda.chineselearning.telegram.service.TelegramBroadcastService;
import com.sakda.chineselearning.telegram.service.TelegramMessageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBroadcastServiceImpl implements TelegramBroadcastService {
	
	private final TelegramPostRepository telegramPostRepository;
	private final TelegramMessageService telegramMessageService;
	
	@Value("${telegram.channel-id}")
	private String channelId;
	
	@Value("${telegram.group-id}")
	private String groupId;

	@Override
	@Transactional
	public void broadcastPost(TelegramPost post) {
		log.info("Starting broadcast for post ID: {}", post.getId());
		
		String targetChatId = null;
		
		if (post.getTarget() == TelegramTargetType.PUBLIC_CHANNEL) {
            targetChatId = channelId;
        } else if (post.getTarget() == TelegramTargetType.PUBLIC_GROUP) {
            targetChatId = groupId;
        } else {
            log.warn("Target type {} not supported yet", post.getTarget());
            return;
        }
		
		String formattedMessage = post.getTitle() + "\n\n" + post.getContent();
		
		try {
			telegramMessageService.sendMessage(targetChatId, formattedMessage);
			
			post.setStatus(TelegramPostStatus.SENT);
			post.setSentAt(LocalDateTime.now());
			log.info("Successfully broadcasted post ID: {}", post.getId());
		} catch (Exception e) {
			
			post.setStatus(TelegramPostStatus.FAILED);
			log.error("Failed to broadcast post ID: {}. Error: {}", post.getId(), e.getMessage());
			
		}
		
		telegramPostRepository.save(post);
		
		
	}

	@Override
	@Transactional
	public void broadcastPostById(Long postId) {
		TelegramPost post = telegramPostRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Telegram post not found with ID: " + postId));
		
		broadcastPost(post);
	}
	
	

}
