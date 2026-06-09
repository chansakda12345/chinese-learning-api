package com.sakda.chineselearning.service;

import java.util.List;

import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.dto.LessonDetailDTO;
import com.sakda.chineselearning.dto.QuizDTO;

public interface LessonService {

    LessonDTO create(LessonDTO lessonDTO);

    List<LessonDTO> getAll();

    LessonDTO getById(Long id);

    LessonDTO update(Long id, LessonDTO lessonDTO);

    void delete(Long id);
    
    LessonDetailDTO getLessonDetails(Long id);
    
    QuizDTO getQuizByLessonId(Long lessonId);
 
}