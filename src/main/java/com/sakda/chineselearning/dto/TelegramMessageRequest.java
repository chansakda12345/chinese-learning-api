package com.sakda.chineselearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TelegramMessageRequest {
	
	private String chat_id;
	
	private String text;

}
