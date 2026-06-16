package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StudentFavoriteWordDTO {
	
	private Long favoriteId;
	
	private Long wordId;
	
	private String chinese;
	
	private String pinyin;
	
	private String english;
	
	private LocalDateTime savedAt;
	
	private LocalDateTime lastReviewedAt;
}
