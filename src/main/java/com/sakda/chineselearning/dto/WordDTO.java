package com.sakda.chineselearning.dto;

import lombok.Data;

import com.sakda.chineselearning.enums.HskLevel;

import jakarta.validation.constraints.NotBlank;

@Data
public class WordDTO {

    private Long id;

    @NotBlank(message = "Chinese is required")
    private String chinese;

    private String pinyin;

    @NotBlank(message = "English is required")
    private String english;

    private String khmer;
    
    @NotBlank(message = "Level is required")
    private HskLevel level;

    private String audioUrl;
}