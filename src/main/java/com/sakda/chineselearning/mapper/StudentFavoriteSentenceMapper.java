package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sakda.chineselearning.dto.StudentFavoriteSentenceDTO;
import com.sakda.chineselearning.entity.StudentFavoriteSentence;

@Mapper(componentModel = "spring")
public interface StudentFavoriteSentenceMapper {
	
	@Mapping(source = "id", target = "favoriteId")
	@Mapping(source = "sentence.chinese", target = "chinese")
	@Mapping(source = "sentence.pinyin", target = "pinyin")
	@Mapping(source = "sentence.english", target = "english")
	@Mapping(source = "sentence.id", target = "sentenceId")
	StudentFavoriteSentenceDTO toDTO(StudentFavoriteSentence favoriteSentence);

}
