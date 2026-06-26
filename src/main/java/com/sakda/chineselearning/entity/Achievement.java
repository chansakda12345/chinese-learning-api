package com.sakda.chineselearning.entity;

import com.sakda.chineselearning.enums.AchievementCategory;
import com.sakda.chineselearning.enums.AchievementCode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "achievements")
@Data
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String badgeIcon;

    private Integer points;
    
    @Enumerated(EnumType.STRING)
    private AchievementCode code;
    
    @Enumerated(EnumType.STRING)
    private AchievementCategory category;
    
    private Integer targetProgress;

    private Boolean active;
}