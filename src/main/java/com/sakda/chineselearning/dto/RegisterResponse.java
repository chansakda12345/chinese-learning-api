package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.entity.Role;

import lombok.Data;

@Data
public class RegisterResponse {
	
	private Long id;
	
	private String username;
	
	private String email;
	
	private Role role;

}
