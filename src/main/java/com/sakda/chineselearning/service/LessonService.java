package com.sakda.chineselearning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sakda.chineselearning.dto.LessonContentDTO;
import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.dto.LessonDetailDTO;
import com.sakda.chineselearning.dto.QuizDTO;
import com.sakda.chineselearning.enums.HskLevel;

public interface LessonService {

    LessonDTO create(LessonDTO lessonDTO);

    List<LessonDTO> getAll();

    LessonDTO getById(Long id);

    LessonDTO update(Long id, LessonDTO lessonDTO);

    void delete(Long id);
    
    LessonDetailDTO getLessonDetails(Long id);
    
    QuizDTO getQuizByLessonId(Long lessonId);
    
    LessonDTO uploadThumbnail(Long lessonId, MultipartFile file);
    
    LessonDTO updatedLesson(Long lessonId, LessonContentDTO dto);
    
    List<LessonDTO> getAll(HskLevel level);
 
}