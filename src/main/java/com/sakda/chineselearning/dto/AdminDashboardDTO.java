package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class AdminDashboardDTO {

    private Long totalUsers;

    private Long totalLessons;

    private Long totalWords;

    private Long totalSentences;

    private Long totalQuestions;

    private Long totalMockExams;

    private Long totalAchievements;

    private Long activeStudents;

    private Long newUsersToday;
}