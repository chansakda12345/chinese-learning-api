package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class LearningAnalyticsDTO {

    private long totalFavoriteWords;

    private long totalFavoriteSentences;

    private long reviewedToday;

    private long reviewedThisWeek;

    private long reviewedThisMonth;

    private long dueWords;

    private long dueSentences;

    private long neverReviewedWords;

    private long neverReviewedSentences;

    private long currentStreak;
}