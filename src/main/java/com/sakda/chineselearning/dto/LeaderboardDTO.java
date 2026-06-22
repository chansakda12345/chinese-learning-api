package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class LeaderboardDTO {
	
	private Integer rank;
	
	private Long userId;
	
	private String username;
	
	private Long totalReviews;
	
	private Integer totalQuizScore;
	
	private Integer totalExamScore;
	
	private Integer streak;
	
	private Long totalPoints;

}
