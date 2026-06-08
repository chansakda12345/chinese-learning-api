package com.sakda.chineselearning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.QuestionDTO;
import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.QuestionMapper;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

	@Override
	public QuestionDTO createQuestion(QuestionDTO questionDTO) {
		
		Question question = 
				questionMapper.toQuestion(questionDTO);
		
		Question savedQuestion = 
				questionRepository.save(question);
		
		return questionMapper.toQuestionDTO(savedQuestion);
	}

	@Override
	public List<QuestionDTO> getQuestions() {
		
		return questionRepository.findAll()
				.stream()
				.map(questionMapper::toQuestionDTO)
				.toList();
	}

	@Override
	public QuestionDTO getQuestionById(Long id) {
		
		Question question = questionRepository.findById(id)
			.orElseThrow(() ->
				new ResourceNotFoundException(
						"Question not found with id: " + id
						)
				);
		return questionMapper.toQuestionDTO(question);
	}

	@Override
	public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
		
		Question question = questionRepository.findById(id)
				.orElseThrow(() ->
					new ResourceNotFoundException(
							"Question not found with id: " + id
							)
					);
		
		question.setQuestionText(questionDTO.getQuestionText());
		question.setCorrectAnswer(questionDTO.getCorrectAnswer());
		
		Question updatedQuestion = questionRepository.save(question);
		
		return questionMapper.toQuestionDTO(updatedQuestion);
	}

	@Override
	public void deleteQuestion(Long id) {
		
		Question question = questionRepository.findById(id)
				.orElseThrow(() ->
					new ResourceNotFoundException(
							"Question not found with id: " + id
							)
					);
		
		questionRepository.delete(question);
		
	}
}