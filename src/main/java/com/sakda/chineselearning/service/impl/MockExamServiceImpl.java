package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.AddQuestionToExamDTO;
import com.sakda.chineselearning.dto.ExamAnswerDTO;
import com.sakda.chineselearning.dto.ExamHistoryDTO;
import com.sakda.chineselearning.dto.ExamResultDTO;
import com.sakda.chineselearning.dto.StartExamDTO;
import com.sakda.chineselearning.dto.SubmitExamDTO;
import com.sakda.chineselearning.entity.MockExam;
import com.sakda.chineselearning.entity.MockExamQuestion;
import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.entity.StudentExamAnswer;
import com.sakda.chineselearning.entity.StudentExamAttempt;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.MockExamQuestionRepository;
import com.sakda.chineselearning.repository.MockExamRepository;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.repository.StudentExamAnswerRepository;
import com.sakda.chineselearning.repository.StudentExamAttemptRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.AchievementService;
import com.sakda.chineselearning.service.MockExamService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MockExamServiceImpl implements MockExamService {

	private final MockExamRepository mockExamRepository;
	private final MockExamQuestionRepository mockExamQuestionRepository;
	private final StudentExamAttemptRepository studentExamAttemptRepository;
	private final StudentExamAnswerRepository studentExamAnswerRepository;
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	private final AchievementService achievementService;

	@Override
	@Transactional
	public ExamResultDTO submitExam(Long examId, SubmitExamDTO submitExamDTO) {

		User user = getCurrentUser();

		StudentExamAttempt attempt = studentExamAttemptRepository.findById(submitExamDTO.getAttemptId()).orElseThrow(
				() -> new ResourceNotFoundException("Exam attempt not found with id: " + submitExamDTO.getAttemptId()));

		if (!attempt.getUser().getId().equals(user.getId())) {
			throw new BusinessException("Exam attempt does not belong to current user");
		}

		if (!attempt.getMockExam().getId().equals(examId)) {
			throw new BusinessException("Attempt does not belong to this exam");
		}

		if (attempt.getSubmittedAt() != null) {
			throw new BusinessException("Exam attempt already submitted");
		}

		List<MockExamQuestion> examQuestions = mockExamQuestionRepository.findByMockExamId(examId);

		Set<Long> examQuestionIds = examQuestions.stream()
				.map(mockExamQuestion -> mockExamQuestion.getQuestion().getId()).collect(Collectors.toSet());

		int totalQuestions = submitExamDTO.getAnswers().size();

		if (totalQuestions == 0) {
			throw new BusinessException("Exam answers cannot be empty");
		}

		int correctAnswers = 0;

		for (ExamAnswerDTO answerDTO : submitExamDTO.getAnswers()) {

			if (!examQuestionIds.contains(answerDTO.getQuestionId())) {
				throw new BusinessException("Question does not belong to this mock exam");
			}

			Question question = questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(
					() -> new ResourceNotFoundException("Question not found with id: " + answerDTO.getQuestionId()));

			boolean correct = answerDTO.getAnswer() != null
					&& answerDTO.getAnswer().equalsIgnoreCase(question.getCorrectAnswer());

			if (correct) {
				correctAnswers++;
			}

			StudentExamAnswer answer = new StudentExamAnswer();

			answer.setAttempt(attempt);
			answer.setQuestion(question);
			answer.setSelectedOption(answerDTO.getAnswer());
			answer.setCorrect(correct);

			studentExamAnswerRepository.save(answer);
		}

		int score = (correctAnswers * 100) / totalQuestions;

		boolean passed = score >= attempt.getMockExam().getPassingScore();

		attempt.setSubmittedAt(LocalDateTime.now());
		attempt.setScore(score);
		attempt.setPassed(passed);

		studentExamAttemptRepository.save(attempt);
		achievementService.checkExamAchievements(user);

		ExamResultDTO result = new ExamResultDTO();

		result.setExamTitle(attempt.getMockExam().getTitle());
		result.setScore(score);
		result.setTotalQuestions(totalQuestions);
		result.setCorrectAnswers(correctAnswers);
		result.setPassed(passed);
		result.setPercentage((double) score);

		return result;
	}

	@Override
	public MockExam createExam(MockExam mockExam) {

		return mockExamRepository.save(mockExam);
	}

	@Override
	public List<MockExam> getAllExams() {

		return mockExamRepository.findAll();
	}

	@Override
	public StartExamDTO startExam(Long examId) {

		User user = getCurrentUser();

		MockExam mockExam = mockExamRepository.findById(examId)
				.orElseThrow(() -> new ResourceNotFoundException("Mock exam not found with id: " + examId));

		StudentExamAttempt attempt = new StudentExamAttempt();

		attempt.setUser(user);
		attempt.setMockExam(mockExam);
		attempt.setStartedAt(LocalDateTime.now());
		attempt.setScore(0);
		attempt.setPassed(false);

		StudentExamAttempt savedAttempt = studentExamAttemptRepository.save(attempt);

		StartExamDTO dto = new StartExamDTO();

		dto.setAttemptId(savedAttempt.getId());
		dto.setExamId(mockExam.getId());
		dto.setExamTitle(mockExam.getTitle());
		dto.setDurationMinutes(mockExam.getDurationMinutes());

		return dto;
	}

	@Override
	public List<ExamHistoryDTO> getExamHistory() {

		User user = getCurrentUser();

		return studentExamAttemptRepository.findByUserOrderByStartedAtDesc(user).stream().map(attempt -> {

			ExamHistoryDTO dto = new ExamHistoryDTO();

			dto.setAttemptId(attempt.getId());
			dto.setExamId(attempt.getMockExam().getId());
			dto.setExamTitle(attempt.getMockExam().getTitle());
			dto.setStartedAt(attempt.getStartedAt());
			dto.setSubmittedAt(attempt.getSubmittedAt());
			dto.setScore(attempt.getScore());
			dto.setPassed(attempt.getPassed());

			return dto;
		}).toList();
	}

	private User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void addQuestionToExam(Long examId, AddQuestionToExamDTO dto) {

		MockExam mockExam = mockExamRepository.findById(examId)
				.orElseThrow(() -> new ResourceNotFoundException("Mock exam not found with id: " + examId));

		Question question = questionRepository.findById(dto.getQuestionId())
				.orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + dto.getQuestionId()));

		MockExamQuestion mockExamQuestion = new MockExamQuestion();

		mockExamQuestion.setMockExam(mockExam);
		mockExamQuestion.setQuestion(question);
		mockExamQuestion.setPoints(dto.getPoints());

		mockExamQuestionRepository.save(mockExamQuestion);

	}

	@Override
	public MockExam getExamById(Long id) {
		return mockExamRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mock exam not found with id: " + id));
	}

	@Override
	public MockExam updateExam(Long id, MockExam mockExam) {
		
		MockExam existingExam = mockExamRepository.findById(id)
									.orElseThrow(() -> new ResourceNotFoundException("Mock exam not found with id: " + id));
		
		existingExam.setTitle(mockExam.getTitle());
		existingExam.setLevel(mockExam.getLevel());
		existingExam.setDurationMinutes(mockExam.getDurationMinutes());
		existingExam.setPassingScore(mockExam.getPassingScore());
		
		return mockExamRepository.save(existingExam);
	}

	@Override
	public MockExam updatePassingScore(Long id, Integer passingScore) {
		
		MockExam existingExam = mockExamRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mock exam not found with id: " + id));
		
		existingExam.setPassingScore(passingScore);
		
		return mockExamRepository.save(existingExam);
	}

	@Override
	public void deleteExam(Long id) {
		
		MockExam existingExam = mockExamRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mock exam not found with id: " + id));
		
		mockExamRepository.delete(existingExam);
		
	}

}
