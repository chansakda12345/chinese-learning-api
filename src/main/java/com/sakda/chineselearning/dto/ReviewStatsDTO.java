package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class ReviewStatsDTO {
	
	private long totalFavoriteWords;
	private long reviewedToday;
	private long dueForReview;
	private long neverReviewed;

}
