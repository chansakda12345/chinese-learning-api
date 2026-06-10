package com.sakda.chineselearning.service;

import com.sakda.chineselearning.dto.OptionDTO;

public interface OptionService {
	
	OptionDTO createOption(
			Long questionId,
			OptionDTO optionDTO
			);

}
