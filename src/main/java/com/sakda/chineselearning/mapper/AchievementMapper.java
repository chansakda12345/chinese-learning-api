package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.sakda.chineselearning.dto.AchievementDTO;
import com.sakda.chineselearning.dto.AchievementProgressDTO;
import com.sakda.chineselearning.dto.AchievementRequestDTO;
import com.sakda.chineselearning.dto.MyAchievementDTO;
import com.sakda.chineselearning.entity.Achievement;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    Achievement toEntity(AchievementRequestDTO dto);

    AchievementDTO toDTO(Achievement achievement);
    
    MyAchievementDTO toMyAchievementDTO(Achievement achievement);
    
    AchievementProgressDTO toAchievementProgressDTO(Achievement achievement);

    void updateEntityFromDTO(
            AchievementRequestDTO dto,
            @MappingTarget Achievement achievement
    );
}