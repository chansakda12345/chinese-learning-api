package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;

import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.entity.Word;

@Mapper(componentModel = "spring")
public interface WordMapper {
	
	Word toEntity(WordDTO dto);
	
	WordDTO toDto(Word entity);
}
