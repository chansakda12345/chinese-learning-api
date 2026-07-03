package com.sakda.chineselearning.telegram.dto;

import java.time.LocalDateTime;

import com.sakda.chineselearning.telegram.enums.TelegramPostStatus;
import com.sakda.chineselearning.telegram.enums.TelegramTargetType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TelegramPostDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Target type is required")
    private TelegramTargetType target;

    private TelegramPostStatus status;

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;
}