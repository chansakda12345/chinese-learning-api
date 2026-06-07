package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.entity.Role;

import lombok.Data;

@Data
public class LoginResponse {
	
	private Long id;
	
	private String username;
	
	private String email;
	
	private Role role;
	
	private String token;

}
