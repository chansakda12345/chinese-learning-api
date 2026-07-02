package com.sakda.chineselearning.telegram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.telegram.TelegramUpdate;
import com.sakda.chineselearning.telegram.service.TelegramCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Telegram API", description = "Webhook from Telegram")
@RestController
@RequestMapping("/api/v1/telegram")
@RequiredArgsConstructor
public class TelegramController {

    private final TelegramCommandService telegramCommandService;
    
    @Operation(summary = "Welcome message to telegram user")
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleUpdate(
            @RequestBody TelegramUpdate update
    ) {
    	
    	if(update.getMessage() == null 
    			|| update.getMessage().getText() == null 
    			|| update.getMessage().getChat() == null) {
    		
    		return ResponseEntity.ok().build();
    	}

    	String command = update.getMessage().getText();
    	
    	String chatId = String.valueOf(update.getMessage().getChat().getId());
    	
    	telegramCommandService.handleCommand(chatId, command);
    	
    	return ResponseEntity.ok().build();
    	
    }
}