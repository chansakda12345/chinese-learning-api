package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.StudentFavoriteSentenceDTO;

public interface StudentFavoriteSentenceService {

    void saveFavoriteSentence(Long sentenceId);

    List<StudentFavoriteSentenceDTO> getMyFavoriteSentences();

    void removeFavoriteSentence(Long favoriteId);

    void reviewFavoriteSentence(Long favoriteId);

    List<StudentFavoriteSentenceDTO> getReviewDueSentences();
}