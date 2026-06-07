package com.sakda.chineselearning.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.LoginRequest;
import com.sakda.chineselearning.dto.LoginResponse;
import com.sakda.chineselearning.dto.RegisterRequest;
import com.sakda.chineselearning.dto.RegisterResponse;
import com.sakda.chineselearning.entity.Role;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.mapper.UserMapper;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.AuthService;
import com.sakda.chineselearning.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@Override
	public RegisterResponse register(RegisterRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException(
					"Email already exists"
			);
		}
		
		User user = new User();
		
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		
		user.setPassword(
				passwordEncoder.encode(request.getPassword())
				);
		
		user.setRole(Role.STUDENT);
		
		User savedUser = userRepository.save(user);
		
		return userMapper.toRegisterResponse(savedUser);
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new RuntimeException(
					"Invalid email or password"
					));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid email or password"); 
		}
		
		String token = jwtService.generateToken(user);
		
		LoginResponse response = userMapper.toLoginResponse(user);
		
		response.setToken(token);
					
		return response;
	}

}
