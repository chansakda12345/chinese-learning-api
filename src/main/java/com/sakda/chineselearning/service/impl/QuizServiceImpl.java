package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.AnswerDTO;
import com.sakda.chineselearning.dto.QuizResultDTO;
import com.sakda.chineselearning.dto.QuizSubmitDTO;
import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.entity.StudentProgress;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.LessonRepository;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.repository.StudentProgressRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
	
	private final LessonRepository lessonRepository;
	
	private final QuestionRepository questionRepository;
	
	private final StudentProgressRepository studentProgressRepository;
	
	private final UserRepository userRepository;

	@Override
	public QuizResultDTO submitQuiz(QuizSubmitDTO quizSubmitDTO) {
		
		Lesson lesson = lessonRepository.findById(quizSubmitDTO.getLessonId())
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + quizSubmitDTO.getLessonId()));
		
		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email)
		        .orElseThrow(() ->
		                new ResourceNotFoundException(
		                        "User not found with email: " + email
		                ));		
		
		int correct = 0;
		
		int total = quizSubmitDTO.getAnswers().size();
		
		if (total == 0) {
		    throw new BusinessException(
		            "Quiz answers cannot be empty"
		    );
		}
		
		for (AnswerDTO answerDTO : quizSubmitDTO.getAnswers()) {

		    Question question = questionRepository.findById(
		            answerDTO.getQuestionId()
		    ).orElseThrow(() ->
		            new ResourceNotFoundException(
		                    "Question not found with id: "
		                            + answerDTO.getQuestionId()
		            )
		    );
		    
		    if (!question.getLesson().getId().equals(lesson.getId())) {
		    	
		    	throw new BusinessException(
		    			"Question does not belong to this lesson"
		    			);
		    }
		    
		    if (answerDTO.getAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
		    	
		    	correct++;
		    }
		}
		
		int score = (correct*100) / total;
		
		StudentProgress progress = studentProgressRepository
			.findByUserEmailAndLessonId(email, lesson.getId())
			.orElse(new StudentProgress());
		
		progress.setUser(user);
		progress.setLesson(lesson);
		
		if (progress.getId() == null || score > progress.getScore()) {
			
			progress.setScore(score);
			progress.setTotalQuestions(total);
			progress.setPercentage((double) score);
			progress.setCompletedAt(LocalDateTime.now());
		}
		
		studentProgressRepository.save(progress);
		
		QuizResultDTO result = new QuizResultDTO();
		
		result.setScore(score);
		result.setCorrect(correct);
		result.setTotal(total);
		
		return result;
		
	}

}
