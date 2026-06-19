package com.sakda.chineselearning.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TelegramUpdate {
	
	@JsonProperty("update_id")
	private Long updateId;
	
	private TelegramMessage message;

}
