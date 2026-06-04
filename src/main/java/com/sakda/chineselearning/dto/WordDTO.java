package com.sakda.chineselearning.dto;

import lombok.Data;
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

    private String level;

    private String audioUrl;
}