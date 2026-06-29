package com.sakda.chineselearning.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminStudentProgressDTO {

    private Long userId;

    private String username;

    private String email;

    private Boolean enabled;

    private Integer totalPoints;

    private Integer achievementCount;

    private Integer completedLessons;

    private Integer completedQuizzes;

    private Integer passedMockExams;

    private Integer favoriteWords;
    
    private Integer favoriteSentences;

    private Integer reviewedWords;

    private Integer dueWords;

    private Boolean telegramConnected;

    private Integer pendingReminders;

    private Integer sentReminders;

    private LocalDateTime nextReminderAt;
}