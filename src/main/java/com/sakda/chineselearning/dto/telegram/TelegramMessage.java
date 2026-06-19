package com.sakda.chineselearning.dto.telegram;

import lombok.Data;

@Data
public class TelegramMessage {
	
	private TelegramChat chat;
	
	private String text;

}
