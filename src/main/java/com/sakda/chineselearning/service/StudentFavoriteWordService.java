package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.StudentFavoriteWordDTO;

public interface StudentFavoriteWordService {
	
	void saveFavoriteWord(Long wordId);
	
	List<StudentFavoriteWordDTO> getMyFavoriteWords();
	
	void removeFavoriteWord(Long favoriteId);
	
	void reviewFavoriteWord(Long favoriteId);
	
	List<StudentFavoriteWordDTO> getReviewDueWords();
}
