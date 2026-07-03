package com.sakda.chineselearning.telegram.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.sakda.chineselearning.telegram.dto.TelegramPostDTO;
import com.sakda.chineselearning.telegram.entity.TelegramPost;

@Mapper(componentModel = "spring")
public interface TelegramPostMapper {
	
	TelegramPostDTO toDTO(TelegramPost entity);
	
	TelegramPost toEntity(TelegramPostDTO dto);
	
	List<TelegramPostDTO> toDTOList(List<TelegramPost> list);

}
