package com.sakda.chineselearning.telegram.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TelegramLinkResponseDTO {
	
	private Boolean success;
	
	private String message;
	
	private String telegramUsername;
	
	private LocalDateTime linkedAt;

}
