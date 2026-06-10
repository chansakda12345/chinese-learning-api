package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;

import com.sakda.chineselearning.dto.OptionDTO;
import com.sakda.chineselearning.entity.Option;

@Mapper(componentModel = "spring")
public interface OptionMapper {
	
	OptionDTO toOptionDTO(Option option);
	
	Option toOption(OptionDTO optionDTO);

}
