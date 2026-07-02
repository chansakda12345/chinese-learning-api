package com.sakda.chineselearning.telegram.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.TelegramMessageService;
import com.sakda.chineselearning.telegram.dto.TelegramLinkRequestDTO;
import com.sakda.chineselearning.telegram.dto.TelegramLinkResponseDTO;
import com.sakda.chineselearning.telegram.entity.TelegramLinkCode;
import com.sakda.chineselearning.telegram.message.TelegramLinkMessageBuilder;
import com.sakda.chineselearning.telegram.repository.TelegramLinkCodeRepository;
import com.sakda.chineselearning.telegram.service.TelegramLinkService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramLinkServiceImpl implements TelegramLinkService {

	private final TelegramLinkCodeRepository telegramLinkCodeRepository;
	
	private final UserRepository userRepository;
	
	private final TelegramMessageService telegramMessageService;
	
	private final TelegramLinkMessageBuilder telegramLinkMessageBuilder;
	
	private static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private static final int CODE_LENGTH = 6;
	
	private final SecureRandom secureRandom = new SecureRandom();
	
	@Override
	@Transactional
	public String generateVerificationCode() {
		
		User user = getCurrentUser();
		
		telegramLinkCodeRepository.deleteByUser(user);

	    String code = generateUniqueCode();
	    
	    TelegramLinkCode linkCode = new TelegramLinkCode();
	    linkCode.setUser(user);
	    linkCode.setCode(code);
	    linkCode.setExpiresAt(LocalDateTime.now().plusMinutes(10));
	    linkCode.setUsed(false);
	    
	    telegramLinkCodeRepository.save(linkCode);

	    return code;
	}

	@Override
	public TelegramLinkResponseDTO verifyTelegramLink(TelegramLinkRequestDTO request) {
		
		return verifyTelegramLink(
	            request.getCode(),
	            request.getChatId()
	    );
		
	}

	@Override
	@Transactional
	public TelegramLinkResponseDTO verifyTelegramLink(String code, String chatId) {
		
		TelegramLinkCode linkCode = telegramLinkCodeRepository.findByCode(code)
		        .orElseThrow(() ->
		                new ResourceNotFoundException("Invalid verification code"));

		if (Boolean.TRUE.equals(linkCode.getUsed())) {
		    throw new BusinessException("Verification code already used");
		}

		if (linkCode.getExpiresAt().isBefore(LocalDateTime.now())) {
		    throw new BusinessException("Verification code expired");
		}

		User user = linkCode.getUser();

		if (user == null) {
		    throw new ResourceNotFoundException("User not found for this verification code");
		}

		if (Boolean.TRUE.equals(user.getTelegramVerified())) {
		    throw new BusinessException("This website account is already linked to Telegram");
		}

		User existingUser = userRepository.findByTelegramChatId(chatId).orElse(null);

		if (existingUser != null && !existingUser.getId().equals(user.getId())) {
		    throw new BusinessException("This Telegram account is already linked to another user");
		}
		
		user.setTelegramChatId(chatId);
		user.setTelegramVerified(true);
		user.setTelegramLinkedAt(LocalDateTime.now());
		
		userRepository.save(user);
		
		linkCode.setUsed(true);
		telegramLinkCodeRepository.save(linkCode);
		
		TelegramLinkResponseDTO response = new TelegramLinkResponseDTO();
		response.setSuccess(true);
		response.setMessage("Telegram account linked successfully.");
		response.setTelegramUsername(user.getTelegramUsername());
		response.setLinkedAt(user.getTelegramLinkedAt());

		return response;
	}
	
	private String generateRandomCode() {
		
		StringBuilder code = new StringBuilder();
		
		for (int i = 0; i < CODE_LENGTH; i++) {
			
			int index = secureRandom.nextInt(CODE_CHARACTERS.length());
			
			code.append(CODE_CHARACTERS.charAt(index));
		}
		
		return code.toString();
	}
	
	private String generateUniqueCode() {
		
		
		String code;
		
		do {
			code = generateRandomCode();
		} while (telegramLinkCodeRepository.existsByCode(code));
		
		return code;
	}
	
	private User getCurrentUser() {
		
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void sendLinkResultMessage(String chatId, String code) {

	    try {
	        verifyTelegramLink(code, chatId);

	        telegramMessageService.sendMessage(
	                chatId,
	                telegramLinkMessageBuilder.buildLinkSuccessMessage()
	        );

	    } catch (BusinessException e) {
	        telegramMessageService.sendMessage(
	                chatId,
	                "❌ " + e.getMessage()
	        );

	    } catch (ResourceNotFoundException e) {
	        telegramMessageService.sendMessage(
	                chatId,
	                telegramLinkMessageBuilder.buildInvalidCodeMessage()
	        );
	    }
	}
}
