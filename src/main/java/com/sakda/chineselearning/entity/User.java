package com.sakda.chineselearning.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private Boolean enabled = true;
	
	@Column(unique = true)
	private String telegramChatId;
	
	@Column(unique = true)
	private String telegramUsername;
	
	private String telegramFirstName;
	
	private LocalDateTime telegramLinkedAt;
	
	@Column(nullable = false)
	private Boolean telegramVerified = false;
	
	@Column(nullable = false)
	private Boolean isPremium = false;
	
	private LocalDateTime premiumExpiresAt;

}
