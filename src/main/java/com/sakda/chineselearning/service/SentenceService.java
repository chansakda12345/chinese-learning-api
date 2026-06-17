package com.sakda.chineselearning.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.enums.HskLevel;

public interface SentenceService {
	
	SentenceDTO createSentence(SentenceDTO dto);
	
	List<SentenceDTO> getSentencesByLesson(Long lessonId);
	
	SentenceDTO getSentenceById(Long id);

	SentenceDTO updateSentence(Long id, SentenceDTO dto);

	void deleteSentence(Long id);
	
	Page<SentenceDTO> getAll(
            HskLevel level,
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

}
