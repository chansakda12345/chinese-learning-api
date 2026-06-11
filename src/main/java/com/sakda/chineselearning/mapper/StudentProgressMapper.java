package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sakda.chineselearning.dto.ProgressDTO;
import com.sakda.chineselearning.entity.StudentProgress;

@Mapper(componentModel = "spring")
public interface StudentProgressMapper {
	
	@Mapping(source = "lesson.id", target = "lessonId")
	@Mapping(source = "lesson.title", target = "lessonTitle")
	ProgressDTO toProgressDTO(StudentProgress studentProgress);

}
