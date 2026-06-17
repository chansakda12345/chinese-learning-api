package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.entity.Sentence;

@Mapper(componentModel = "spring")
public interface SentenceMapper {

    Sentence toEntity(SentenceDTO dto);

    @Mapping(source = "lesson.id", target = "lessonId")
    SentenceDTO toSentenceDTO(Sentence entity);
}