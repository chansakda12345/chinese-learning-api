package com.sakda.chineselearning.entity;

import java.time.LocalDateTime;

import com.sakda.chineselearning.enums.HskLevel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mock_exams")
@Data
public class MockExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private HskLevel level;

    private LocalDateTime createdAt;

    private Integer durationMinutes;

    private Integer passingScore;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}