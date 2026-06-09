package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;

import com.sakda.chineselearning.dto.QuestionDTO;
import com.sakda.chineselearning.dto.QuestionStudentDTO;
import com.sakda.chineselearning.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
	
	QuestionDTO toQuestionDTO(Question question);
	
	Question toQuestion(QuestionDTO questionDTO);
	
	QuestionStudentDTO toQuestionStudentDTO(Question question);
}
