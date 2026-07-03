package com.sakda.chineselearning.telegram.model;

import lombok.Data;

@Data
public class TelegramMessage {
	
	private TelegramChat chat;
	
	private String text;

}
