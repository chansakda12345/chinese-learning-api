package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.HskLevel;

import lombok.Data;

@Data
public class SentenceDTO {

    private Long id;

    private String chinese;

    private String pinyin;

    private String english;

    private String khmer;

    private String audioUrl;

    private HskLevel level;

    private Long lessonId;
}