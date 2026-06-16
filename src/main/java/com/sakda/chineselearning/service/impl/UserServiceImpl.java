package com.sakda.chineselearning.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.TelegramChatIdRequest;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	
	@Override
	public void updateTelegramChatId(TelegramChatIdRequest telegramChatId) {
		
		User user = getCurrentUser();
		
		user.setTelegramChatId(telegramChatId.getTelegramChatId());
		
		userRepository.save(user);
		
	}
	
	
	private User getCurrentUser() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User not found"));
	}

}
