package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StudentFavoriteSentenceDTO {
	
    private Long favoriteId;

    private Long sentenceId;

    private String chinese;

    private String pinyin;

    private String english;

    private LocalDateTime savedAt;

    private LocalDateTime lastReviewedAt;

    private Integer reviewCount;

    private LocalDateTime nextReviewAt;

}
