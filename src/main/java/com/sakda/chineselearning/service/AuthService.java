package com.sakda.chineselearning.service;

import com.sakda.chineselearning.dto.LoginRequest;
import com.sakda.chineselearning.dto.LoginResponse;
import com.sakda.chineselearning.dto.RegisterRequest;
import com.sakda.chineselearning.dto.RegisterResponse;

public interface AuthService {
	
	RegisterResponse register(RegisterRequest request);
	
	LoginResponse login(LoginRequest request);

}
