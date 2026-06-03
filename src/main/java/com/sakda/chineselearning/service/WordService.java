package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.WordDTO;

public interface WordService {

    WordDTO create(WordDTO dto);

    List<WordDTO> getAll();

    WordDTO getById(Long id);

    WordDTO update(Long id, WordDTO dto);

    void delete(Long id);
}