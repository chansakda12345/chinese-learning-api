package com.sakda.chineselearning.service.impl;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.OptionDTO;
import com.sakda.chineselearning.entity.Option;
import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.OptionMapper;
import com.sakda.chineselearning.repository.OptionRepository;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.service.OptionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
	
	private final OptionRepository optionRepository;
	private final QuestionRepository questionRepository;
	private final OptionMapper optionMapper;
	
	@Override
	public OptionDTO createOption(Long questionId, OptionDTO optionDTO) {
		
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
		
		Option option = optionMapper.toOption(optionDTO);
		
		option.setQuestion(question);
		
		Option savedOption = optionRepository.save(option);
		
		return optionMapper.toOptionDTO(savedOption);
	}
	

}
