package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class WordDTO {

    private Long id;

    private String chinese;

    private String pinyin;

    private String english;

    private String khmer;

    private String level;

    private String audioUrl;
}