package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sakda.chineselearning.dto.StudentFavoriteWordDTO;
import com.sakda.chineselearning.entity.StudentFavoriteWord;

@Mapper(componentModel = "spring")
public interface StudentFavoriteWordMapper {
	
	@Mapping(source = "id", target = "favoriteId")
	@Mapping(source = "word.id", target = "wordId")
	@Mapping(source = "word.chinese", target = "chinese")
	@Mapping(source = "word.pinyin", target = "pinyin")
	@Mapping(source = "word.english", target = "english")
	StudentFavoriteWordDTO toDTO(StudentFavoriteWord favoriteWord);

}
