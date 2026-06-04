package com.sakda.chineselearning.service;

import com.sakda.chineselearning.dto.LessonDTO;

import java.util.List;

public interface LessonService {

    LessonDTO create(LessonDTO lessonDTO);

    List<LessonDTO> getAll();

    LessonDTO getById(Long id);

    LessonDTO update(Long id, LessonDTO lessonDTO);

    void delete(Long id);
}