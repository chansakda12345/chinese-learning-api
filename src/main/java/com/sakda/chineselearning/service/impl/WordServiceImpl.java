package com.sakda.chineselearning.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.entity.Word;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.WordMapper;
import com.sakda.chineselearning.repository.LessonRepository;
import com.sakda.chineselearning.repository.WordRepository;
import com.sakda.chineselearning.service.WordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
	
	private final WordRepository wordRepository;
	private final WordMapper wordMapper;
	private final LessonRepository lessonRepository;

	@Override
	public WordDTO create(WordDTO dto) {

	    return wordMapper.toDto (
	            wordRepository.save(
	                    wordMapper.toEntity(dto)
	            )
	    );
	}

	@Override
	public Page<WordDTO> getAll(String keyword, int page, int size, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		if (keyword == null || keyword.isBlank()) {
			return wordRepository.findAll(pageable)
					.map(wordMapper::toDto);
		}
		
		return wordRepository
	            .findByChineseContainingIgnoreCaseOrPinyinContainingIgnoreCaseOrEnglishContainingIgnoreCaseOrKhmerContainingIgnoreCase(
	                    keyword,
	                    keyword,
	                    keyword,
	                    keyword,
	                    pageable
	            )
	            .map(wordMapper::toDto);
	}

	@Override
	public WordDTO getById(Long id) {
		Word word = wordRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Word not found"));
		return wordMapper.toDto(word);
	}

	@Override
	public WordDTO update(Long id, WordDTO dto) {
		
		Word word = wordRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Word not found"));
		
		word.setChinese(dto.getChinese());
		word.setPinyin(dto.getPinyin());
		word.setEnglish(dto.getEnglish());
		word.setKhmer(dto.getKhmer());
		word.setLevel(dto.getLevel());
		word.setAudioUrl(dto.getAudioUrl());
		
		Word updateWord = wordRepository.save(word);
		
		return wordMapper.toDto(updateWord);
	}

	@Override
	public void delete(Long id) {
		
		Word word = wordRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Word not found"));
		
		wordRepository.delete(word);
		
	}

	@Override
	public WordDTO assignLesson(Long wordId, Long lessonId) {

	    Word word = wordRepository.findById(wordId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Word not found with id: " + wordId));

	    Lesson lesson = lessonRepository.findById(lessonId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Lesson not found with id: " + lessonId));
	    
	    word.setLesson(lesson);
	    Word savedWord = wordRepository.save(word);

	    return wordMapper.toDto(savedWord);
	}

}
