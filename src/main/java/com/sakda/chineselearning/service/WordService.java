package com.sakda.chineselearning.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.sakda.chineselearning.dto.WordDTO;

public interface WordService {

    WordDTO create(WordDTO dto);

	Page<WordDTO> getAll(
			String keyword, 
			int page, 
			int size,
			String sortBy,
			String sortDir
			);

    WordDTO getById(Long id);

    WordDTO update(Long id, WordDTO dto);

    void delete(Long id);
    
    WordDTO assignLesson(Long wordId, Long lessonId);
    
    WordDTO uploadAudio(Long wordId, MultipartFile file);
    
    WordDTO getRandomWord();
}