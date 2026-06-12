package com.sakda.chineselearning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sakda.chineselearning.dto.LessonDTO;
import com.sakda.chineselearning.dto.LessonDetailDTO;
import com.sakda.chineselearning.dto.QuizDTO;
import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.LessonMapper;
import com.sakda.chineselearning.mapper.QuestionMapper;
import com.sakda.chineselearning.repository.LessonRepository;
import com.sakda.chineselearning.service.FileStorageService;
import com.sakda.chineselearning.service.LessonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final QuestionMapper questionMapper;
    private final FileStorageService fileStorageService;

    @Override
    public LessonDTO create(LessonDTO lessonDTO) {
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    @Override
    public List<LessonDTO> getAll() {
        return lessonRepository.findAll()
                .stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    @Override
    public LessonDTO getById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        return lessonMapper.toDto(lesson);
    }

    @Override
    public LessonDTO update(Long id, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        lesson.setTitle(lessonDTO.getTitle());
        lesson.setDescription(lessonDTO.getDescription());

        Lesson updatedLesson = lessonRepository.save(lesson);

        return lessonMapper.toDto(updatedLesson);
    }

    @Override
    public void delete(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        lessonRepository.delete(lesson);
    }

    @Override
    public LessonDetailDTO getLessonDetails(Long id) {

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson not found with id: " + id));

        return lessonMapper.toDetailDto(lesson);
    }

	@Override
	public QuizDTO getQuizByLessonId(Long lessonId) {
		
		Lesson lesson = lessonRepository.findById(lessonId)
				.orElseThrow(() -> 
					new ResourceNotFoundException("Lesson not found with id: " + lessonId
		));
		
		QuizDTO quizDTO = new QuizDTO();
		
		quizDTO.setLessonId(lesson.getId());
		quizDTO.setTitle(lesson.getTitle());
		
		quizDTO.setQuestions(
				lesson.getQuestions()
				.stream()
				.map(questionMapper::toQuestionStudentDTO)
				.toList()
		);
		return quizDTO;
	}

	@Override
	public LessonDTO uploadThumbnail(Long lessonId, MultipartFile file) {
		
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id" + lessonId));
		
		String imageUrl = fileStorageService.uploadImage(file);
		
		lesson.setThumbnailUrl(imageUrl);
		
		Lesson savedLesson = lessonRepository.save(lesson);
		
		return lessonMapper.toDto(savedLesson);
	}
}