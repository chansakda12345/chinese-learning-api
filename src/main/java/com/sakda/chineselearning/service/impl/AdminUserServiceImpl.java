package com.sakda.chineselearning.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.AdminStudentProgressDTO;
import com.sakda.chineselearning.dto.AdminUserDTO;
import com.sakda.chineselearning.entity.StudentExamAttempt;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.LearningReminderRepository;
import com.sakda.chineselearning.repository.StudentExamAttemptRepository;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.TelegramQuizSessionRepository;
import com.sakda.chineselearning.repository.UserAchievementRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.AdminUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
	
	private static final int DEFAULT_PASSING_SCORE = 60;

    private final UserRepository userRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final StudentFavoriteWordRepository studentFavoriteWordRepository;
    private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;
    private final TelegramQuizSessionRepository telegramQuizSessionRepository;
    private final StudentExamAttemptRepository studentExamAttemptRepository;
    private final LearningReminderRepository learningReminderRepository;

    @Override
    public Page<AdminUserDTO> getUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(this::mapToAdminUserDTO);
    }

    @Override
    public Page<AdminUserDTO> searchUsers(String keyword, Pageable pageable) {

        return userRepository
                .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword,
                        keyword,
                        pageable
                )
                .map(this::mapToAdminUserDTO);
    }

    private AdminUserDTO mapToAdminUserDTO(User user) {

        AdminUserDTO dto = new AdminUserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setTelegramConnected(user.getTelegramChatId() != null);
        dto.setAchievementCount(
                userAchievementRepository.countByUser(user).intValue()
        );

        dto.setTotalPoints(0);
        dto.setEnabled(user.getEnabled());
        dto.setCreatedAt(null);
        dto.setLastLoginAt(null);

        return dto;
    }

	@Override
	public AdminUserDTO enableUser(Long userId) {
		
		User user = userRepository.findById(userId)
						.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		user.setEnabled(true);
		
		User savedUser = userRepository.save(user);
		
		return mapToAdminUserDTO(savedUser);		
	}

	@Override
	public AdminUserDTO disableUser(Long userId) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "User not found with id: " + userId));

	    user.setEnabled(false);

	    User savedUser = userRepository.save(user);

	    return mapToAdminUserDTO(savedUser);
	}

	@Override
	public AdminStudentProgressDTO getStudentProgress(Long userId) {
		
		User user = userRepository.findById(userId)
						.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		AdminStudentProgressDTO dto = new AdminStudentProgressDTO();
		
		dto.setUserId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setEnabled(user.getEnabled());
		
		dto.setAchievementCount(
				userAchievementRepository.countByUser(user).intValue()
		);
		
		dto.setFavoriteWords(
		        studentFavoriteWordRepository.countByUser(user).intValue()
		);

		dto.setFavoriteSentences(
		        studentFavoriteSentenceRepository.countByUser(user).intValue()
		);
		
		dto.setCompletedQuizzes(
				telegramQuizSessionRepository.countByUserAndAnsweredTrue(user).intValue()
		);
		
		dto.setPassedMockExams(
		        calculatePassedExamCount(user).intValue()
		);
		
		dto.setPendingReminders(
				learningReminderRepository.countByUserAndSentFalse(user).intValue()
		);
		
		dto.setSentReminders(
				learningReminderRepository.countByUserAndSentTrue(user).intValue()
		);
		
		dto.setNextReminderAt(
				learningReminderRepository.findFirstByUserAndSentFalseOrderByRemindAtAsc(user)
											.map(reminder -> reminder.getRemindAt())
											.orElse(null)
		);
		
		dto.setTotalPoints(0);
		dto.setCompletedLessons(0);
		dto.setReviewedWords(0);
		dto.setDueWords(0);
		dto.setTelegramConnected(user.getTelegramChatId() != null);
		
		return dto;
	}
	
	private Long calculatePassedExamCount(User user) {

	    List<StudentExamAttempt> attempts =
	            studentExamAttemptRepository.findByUser(user);

	    Map<Long, Integer> bestScorePerExam = new HashMap<>();
	    Map<Long, Integer> passingScorePerExam = new HashMap<>();

	    for (StudentExamAttempt attempt : attempts) {

	        if (attempt.getMockExam() == null) {
	            continue;
	        }

	        Long examId = attempt.getMockExam().getId();

	        Integer score =
	                attempt.getScore() == null
	                        ? 0
	                        : attempt.getScore();

	        Integer passingScore =
	                attempt.getMockExam().getPassingScore() == null
	                        ? DEFAULT_PASSING_SCORE
	                        : attempt.getMockExam().getPassingScore();

	        passingScorePerExam.put(examId, passingScore);

	        Integer currentBest =
	                bestScorePerExam.getOrDefault(examId, 0);

	        if (score > currentBest) {
	            bestScorePerExam.put(examId, score);
	        }
	    }

	    return bestScorePerExam.entrySet()
	            .stream()
	            .filter(entry -> {
	                Long examId = entry.getKey();
	                Integer bestScore = entry.getValue();

	                Integer passingScore =
	                        passingScorePerExam.getOrDefault(
	                                examId,
	                                DEFAULT_PASSING_SCORE
	                        );

	                return bestScore >= passingScore;
	            })
	            .count();
	}
}