package com.sakda.chineselearning.telegram.entity;

import java.time.LocalDateTime;

import com.sakda.chineselearning.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "telegram_link_codes")
@Entity
public class TelegramLinkCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String code;
	
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	
	@Column(nullable = false)
	private Boolean used = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@PrePersist
	public void onCreate() {
	    createdAt = LocalDateTime.now();
	}

}
