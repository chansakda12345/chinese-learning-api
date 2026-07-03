package com.sakda.chineselearning.telegram.entity;

import java.time.LocalDateTime;
import com.sakda.chineselearning.telegram.enums.TelegramPostStatus;
import com.sakda.chineselearning.telegram.enums.TelegramTargetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "telegram_posts")
public class TelegramPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TelegramTargetType target;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TelegramPostStatus status = TelegramPostStatus.DRAFT;
    
    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        
        if (this.status == null) {
        	this.status = TelegramPostStatus.DRAFT;
        }
    }
}