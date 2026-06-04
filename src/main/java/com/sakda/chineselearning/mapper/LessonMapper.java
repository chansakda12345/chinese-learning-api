package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;

import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {
	
	LessonDTO toDto(Lesson lesson);
	
	Lesson toEntity(LessonDTO lessonDTO);
}
