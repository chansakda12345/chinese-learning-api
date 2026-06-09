package com.sakda.chineselearning.service.impl;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.AnswerDTO;
import com.sakda.chineselearning.dto.QuizResultDTO;
import com.sakda.chineselearning.dto.QuizSubmitDTO;
import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.LessonRepository;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.service.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
	
	private final LessonRepository lessonRepository;
	
	private final QuestionRepository questionRepository;

	@Override
	public QuizResultDTO submitQuiz(QuizSubmitDTO quizSubmitDTO) {
		
		Lesson lesson = lessonRepository.findById(quizSubmitDTO.getLessonId())
			.orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + quizSubmitDTO.getLessonId()));
		
		int correct = 0;
		
		int total = quizSubmitDTO.getAnswers().size();
		
		if (total == 0) {
		    throw new RuntimeException(
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
		    	
		    	throw new RuntimeException(
		    			"Question does not belong to this lesson"
		    			);
		    }
		    
		    if (answerDTO.getAnswer().equalsIgnoreCase(question.getCorrectAnswer())) {
		    	
		    	correct++;
		    }
		}
		
		int score = (correct*100) / total;
		
		QuizResultDTO result = new QuizResultDTO();
		
		result.setScore(score);
		result.setCorrect(correct);
		result.setTotal(total);
		
		return result;
		
	}

}
