package com.sakda.chineselearning.telegram.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TelegramLinkRequestDTO {
	
	@NotBlank
	private String code;
	
	@NotBlank
	private String chatId;

}
