package com.sakda.chineselearning.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.entity.Sentence;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.SentenceMapper;
import com.sakda.chineselearning.repository.LessonRepository;
import com.sakda.chineselearning.repository.SentenceRepository;
import com.sakda.chineselearning.service.SentenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {
	
	private final SentenceRepository sentenceRepository;
	private final LessonRepository lessonRepository;
	private final SentenceMapper sentenceMapper;
	
	@Override
	public SentenceDTO createSentence(SentenceDTO dto) {
		
		Lesson lesson = lessonRepository.findById(dto.getLessonId())
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + dto.getLessonId()));
		
		Sentence sentence = sentenceMapper.toEntity(dto);
		
		sentence.setLesson(lesson);
		
		Sentence savedSentence = sentenceRepository.save(sentence);
		
		return sentenceMapper.toSentenceDTO(savedSentence);
	}

	@Override
	public List<SentenceDTO> getSentencesByLesson(Long lessonId) {
		
		Lesson lesson = lessonRepository.findById(lessonId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Lesson not found with id: " + lessonId));
		
		return sentenceRepository.findByLesson(lesson)
	            .stream()
	            .map(sentenceMapper::toSentenceDTO)
	            .toList();
	}

	@Override
	public SentenceDTO getSentenceById(Long id) {
		
		Sentence sentence = sentenceRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Sentence not found by id: " + id));
		
		return sentenceMapper.toSentenceDTO(sentence);
	}

	@Override
	public SentenceDTO updateSentence(Long id, SentenceDTO dto) {
		
		Sentence sentence = sentenceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sentence not found by id: " + id));
		
		Lesson lesson = lessonRepository.findById(dto.getLessonId())
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + dto.getLessonId()));

		sentence.setChinese(dto.getChinese());	
		sentence.setPinyin(dto.getPinyin());
		sentence.setEnglish(dto.getEnglish());
		sentence.setKhmer(dto.getKhmer());
		sentence.setAudioUrl(dto.getAudioUrl());
	    sentence.setLevel(dto.getLevel());
	    sentence.setLesson(lesson);
		
		Sentence savedSentence = sentenceRepository.save(sentence);
		
		return sentenceMapper.toSentenceDTO(savedSentence);
		
	}

	@Override
	public void deleteSentence(Long id) {

	    Sentence sentence = sentenceRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Sentence not found with id: " + id));

	    sentenceRepository.delete(sentence);
	}

	@Override
	public Page<SentenceDTO> getAll(HskLevel level, String keyword, int page, int size, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("desc")
	            ? Sort.by(sortBy).descending()
	            : Sort.by(sortBy).ascending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		boolean hasKeyword = keyword != null && !keyword.isBlank();
		
		if (level == null && !hasKeyword) {
			
			return sentenceRepository.findAll(pageable)
					.map(sentenceMapper::toSentenceDTO);
		}
		
		if (level != null && !hasKeyword) {
			
			return sentenceRepository.findByLevel(level, pageable)
					.map(sentenceMapper::toSentenceDTO);
		}
		
		if (level == null && hasKeyword) {
			
			return sentenceRepository.findByChineseContainingIgnoreCase(keyword, pageable)
					.map(sentenceMapper::toSentenceDTO);
		}
		
		return sentenceRepository.findByLevelAndChineseContainingIgnoreCase(level, keyword, pageable)
				.map(sentenceMapper::toSentenceDTO);
	}

}
