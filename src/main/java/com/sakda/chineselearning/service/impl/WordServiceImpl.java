package com.sakda.chineselearning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.entity.Word;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.WordMapper;
import com.sakda.chineselearning.repository.WordRepository;
import com.sakda.chineselearning.service.WordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
	
	private final WordRepository wordRepository;
	private final WordMapper wordMapper;

	@Override
	public WordDTO create(WordDTO dto) {

	    return wordMapper.toDto (
	            wordRepository.save(
	                    wordMapper.toEntity(dto)
	            )
	    );
	}

	@Override
	public List<WordDTO> getAll() {
		return wordRepository.findAll()
			.stream()
			.map(wordMapper::toDto)
			.toList();
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

}
