package com.sakda.chineselearning.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.sakda.chineselearning.entity.User;

public interface JwtService {
	
	String generateToken(User user);
	
	String extractUsername(String token);
		
	boolean isTokenValid(String token, UserDetails userDetails);
}
