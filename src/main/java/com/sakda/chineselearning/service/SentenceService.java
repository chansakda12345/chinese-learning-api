package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.SentenceDTO;

public interface SentenceService {
	
	SentenceDTO createSentence(SentenceDTO dto);
	
	List<SentenceDTO> getAllSentences();
	
	List<SentenceDTO> getSentencesByLesson(Long lessonId);
	
	SentenceDTO getSentenceById(Long id);

	SentenceDTO updateSentence(Long id, SentenceDTO dto);

	void deleteSentence(Long id);

}
