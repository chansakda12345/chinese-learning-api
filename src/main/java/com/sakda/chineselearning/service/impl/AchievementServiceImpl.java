package com.sakda.chineselearning.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.AchievementDTO;
import com.sakda.chineselearning.dto.AchievementProgressDTO;
import com.sakda.chineselearning.dto.AchievementRequestDTO;
import com.sakda.chineselearning.dto.MyAchievementDTO;
import com.sakda.chineselearning.entity.Achievement;
import com.sakda.chineselearning.entity.StudentExamAttempt;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.UserAchievement;
import com.sakda.chineselearning.enums.AchievementCategory;
import com.sakda.chineselearning.enums.AchievementCode;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.AchievementMapper;
import com.sakda.chineselearning.repository.AchievementRepository;
import com.sakda.chineselearning.repository.StudentExamAttemptRepository;
import com.sakda.chineselearning.repository.StudentFavoriteSentenceRepository;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.TelegramQuizSessionRepository;
import com.sakda.chineselearning.repository.UserAchievementRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.AchievementService;
import com.sakda.chineselearning.service.TelegramMessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
	
	private static final int DEFAULT_PASSING_SCORE = 60;

	private final UserAchievementRepository userAchievementRepository;

	private final TelegramMessageService telegramService;

	private final AchievementRepository achievementRepository;

	private final StudentFavoriteWordRepository studentFavoriteWordRepository;

	private final StudentFavoriteSentenceRepository studentFavoriteSentenceRepository;

	private final AchievementMapper achievementMapper;

	private final TelegramQuizSessionRepository telegramQuizSessionRepository;

	private final StudentExamAttemptRepository studentExamAttemptRepository;

	private final UserRepository userRepository;

	@Override
	public AchievementDTO createAchievement(AchievementRequestDTO dto) {

		Achievement achievement = achievementMapper.toEntity(dto);

		if (achievement.getActive() == null) {
			achievement.setActive(true);
		}

		Achievement savedAchievement = achievementRepository.save(achievement);

		return achievementMapper.toDTO(savedAchievement);
	}

	@Override
	public List<AchievementDTO> getAllAchievements() {

		return achievementRepository.findAll().stream().map(achievementMapper::toDTO).toList();
	}

	@Override
	public AchievementDTO updateAchievement(Long id, AchievementRequestDTO dto) {

		Achievement achievement = achievementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Achievement not found with id: " + id));

		achievementMapper.updateEntityFromDTO(dto, achievement);

		Achievement savedAchievement = achievementRepository.save(achievement);

		return achievementMapper.toDTO(savedAchievement);
	}

	@Override
	public void checkWordAchievements(User user) {

		Long savedWords = studentFavoriteWordRepository.countByUser(user);
		
		Achievement firstWordSaved = findAchievement(AchievementCode.WORD_FIRST);
		
		Achievement wordCollector = findAchievement(AchievementCode.WORD_50);
		
		Achievement wordMaster = findAchievement(AchievementCode.WORD_200);

		if (savedWords >= firstWordSaved.getTargetProgress()) {
	        awardAchievement(user, firstWordSaved);
	    }

	    if (savedWords >= wordCollector.getTargetProgress()) {
	        awardAchievement(user, wordCollector);
	    }

	    if (savedWords >= wordMaster.getTargetProgress()) {
	        awardAchievement(user, wordMaster);
	    }
	}

	@Override
	public void checkSentenceAchievements(User user) {

	    Long savedSentences =
	            studentFavoriteSentenceRepository.countByUser(user);

	    Achievement firstSentenceSaved =
	            findAchievement(AchievementCode.SENTENCE_FIRST);

	    Achievement sentenceCollector =
	            findAchievement(AchievementCode.SENTENCE_50);

	    Achievement sentenceMaster =
	            findAchievement(AchievementCode.SENTENCE_200);

	    if (savedSentences >= firstSentenceSaved.getTargetProgress()) {
	        awardAchievement(user, firstSentenceSaved);
	    }

	    if (savedSentences >= sentenceCollector.getTargetProgress()) {
	        awardAchievement(user, sentenceCollector);
	    }

	    if (savedSentences >= sentenceMaster.getTargetProgress()) {
	        awardAchievement(user, sentenceMaster);
	    }
	}

	@Override
	public void checkReviewAchievements(User user) {

	    Long wordReviews =
	            studentFavoriteWordRepository.sumReviewCountByUser(user);
	    
	    if (wordReviews == null) {
	    	wordReviews = 0L;
	    }

	    Long sentenceReviews =
	            studentFavoriteSentenceRepository.sumReviewCountByUser(user);
	    
	    if (sentenceReviews == null) {
	    	sentenceReviews = 0L;
	    }

	    Long totalReviews =
	            wordReviews + sentenceReviews;

	    Achievement firstReview =
	            findAchievement(AchievementCode.REVIEW_FIRST);

	    Achievement reviewWarrior =
	            findAchievement(AchievementCode.REVIEW_100);

	    Achievement reviewMaster =
	            findAchievement(AchievementCode.REVIEW_500);

	    if (totalReviews >= firstReview.getTargetProgress()) {
	        awardAchievement(user, firstReview);
	    }

	    if (totalReviews >= reviewWarrior.getTargetProgress()) {
	        awardAchievement(user, reviewWarrior);
	    }

	    if (totalReviews >= reviewMaster.getTargetProgress()) {
	        awardAchievement(user, reviewMaster);
	    }
	}

	@Override
	public void checkQuizAchievements(User user) {

	    Long completeQuizzes =
	            telegramQuizSessionRepository.countByUserAndAnsweredTrue(user);

	    Achievement quizBeginner =
	            findAchievement(AchievementCode.QUIZ_FIRST);

	    Achievement quizMaster =
	            findAchievement(AchievementCode.QUIZ_100);

	    if (completeQuizzes >= quizBeginner.getTargetProgress()) {
	        awardAchievement(user, quizBeginner);
	    }

	    if (completeQuizzes >= quizMaster.getTargetProgress()) {
	        awardAchievement(user, quizMaster);
	    }
	}

	@Override
	public void checkExamAchievements(User user) {

	    Long passedExamCount =
	            calculatePassedExamCount(user);

	    Achievement examPasser =
	            findAchievement(AchievementCode.EXAM_FIRST);

	    if (passedExamCount >= examPasser.getTargetProgress()) {
	        awardAchievement(user, examPasser);
	    }
	}

	@Override
	public void awardAchievement(User user, Achievement achievement) {

		boolean alreadyEarned = userAchievementRepository.existsByUserAndAchievement(user, achievement);

		if (alreadyEarned) {
			return;
		}

		UserAchievement userAchievement = new UserAchievement();

		userAchievement.setUser(user);
		userAchievement.setAchievement(achievement);

		userAchievementRepository.save(userAchievement);

		if (user.getTelegramChatId() != null) {

			telegramService.sendMessage(user.getTelegramChatId(), """
					🏆 Achievement Unlocked!

					%s

					Reward: %d Points
					""".formatted(achievement.getName(), achievement.getPoints()));

		}

	}

	@Override
	public List<MyAchievementDTO> getMyAchievements() {

		User user = getCurrentUser();

		List<Achievement> achievements = achievementRepository.findAll();

		List<UserAchievement> userAchievements = userAchievementRepository.findByUser(user);

		Map<Long, UserAchievement> earnedMap = userAchievements.stream().collect(Collectors.toMap(
				userAchievement -> userAchievement.getAchievement().getId(), userAchievement -> userAchievement));

		List<MyAchievementDTO> list = achievements.stream().map(achievement -> {

			MyAchievementDTO dto = achievementMapper.toMyAchievementDTO(achievement);

			UserAchievement userAchievement = earnedMap.get(achievement.getId());

			if (userAchievement != null) {
				dto.setEarned(true);
				dto.setEarnedAt(userAchievement.getEarnedAt());
			} else {
				dto.setEarned(false);
				dto.setEarnedAt(null);
			}

			return dto;

		}).toList();

		return list;
	}

	private User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public List<AchievementProgressDTO> getAchievementProgress() {

		User user = getCurrentUser();

		List<Achievement> achievements = achievementRepository.findAll();

		Long savedWords = studentFavoriteWordRepository.countByUser(user);

		Long savedSentences = studentFavoriteSentenceRepository.countByUser(user);

		Long wordReviews = studentFavoriteWordRepository.sumReviewCountByUser(user);
		
		if (wordReviews == null) {
		    wordReviews = 0L;
		}

		Long sentenceReviews = studentFavoriteSentenceRepository.sumReviewCountByUser(user);
		
		if (sentenceReviews == null) {
		    sentenceReviews = 0L;
		}

		Long totalReviews = wordReviews + sentenceReviews;

		Long completedQuizzes = telegramQuizSessionRepository.countByUserAndAnsweredTrue(user);

		Long passedExams = calculatePassedExamCount(user);
		
		return achievements.stream()
	            .map(achievement -> {

	            	AchievementProgressDTO dto =
	            	        achievementMapper.toAchievementProgressDTO(achievement);

	                Integer currentProgress =
	                        getCurrentProgress(
	                        		achievement.getCategory(),
	                                savedWords,
	                                savedSentences,
	                                totalReviews,
	                                completedQuizzes,
	                                passedExams
	                        );

	                dto.setCurrentProgress(currentProgress);

	                Integer targetProgress =
	                        achievement.getTargetProgress() == null
	                                ? 0
	                                : achievement.getTargetProgress();

	                dto.setCompleted(
	                        currentProgress >= targetProgress
	                        && targetProgress > 0
	                );

	                return dto;
	            })
	            .toList();
	}

	private Integer getCurrentProgress(
	        AchievementCategory category,
	        Long savedWords,
	        Long savedSentences,
	        Long totalReviews,
	        Long completedQuizzes,
	        Long passedExams
	) {

	    if (category == null) {
	        return 0;
	    }

	    return switch (category) {
	        case WORD -> savedWords.intValue();
	        case SENTENCE -> savedSentences.intValue();
	        case REVIEW -> totalReviews.intValue();
	        case QUIZ -> completedQuizzes.intValue();
	        case EXAM -> passedExams.intValue();
	    };
	}
	
	private Achievement findAchievement(AchievementCode code) {
		
		return achievementRepository.findByCode(code)
				.orElseThrow(() ->
                new ResourceNotFoundException(
                        "Achievement not found: " + code));
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
	                        passingScorePerExam.getOrDefault(examId, DEFAULT_PASSING_SCORE);

	                return bestScore >= passingScore;
	            })
	            .count();
	}

	@Override
	public AchievementDTO getAchievementById(Long id) {

	    Achievement achievement = achievementRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Achievement not found with id: " + id));

	    return achievementMapper.toDTO(achievement);
	}

	@Override
	public void deleteAchievement(Long id) {

	    Achievement achievement = achievementRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Achievement not found with id: " + id));

	    achievementRepository.delete(achievement);
	}

	@Override
	public AchievementDTO activateAchievement(Long id) {

	    Achievement achievement = achievementRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Achievement not found with id: " + id));

	    achievement.setActive(true);

	    Achievement savedAchievement = achievementRepository.save(achievement);

	    return achievementMapper.toDTO(savedAchievement);
	}

	@Override
	public AchievementDTO deactivateAchievement(Long id) {

	    Achievement achievement = achievementRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Achievement not found with id: " + id));

	    achievement.setActive(false);

	    Achievement savedAchievement = achievementRepository.save(achievement);

	    return achievementMapper.toDTO(savedAchievement);
	}

}
