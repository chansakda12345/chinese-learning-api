package com.sakda.chineselearning.telegram.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.Option;
import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.entity.TelegramQuizSession;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.enums.HskLevel;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.repository.QuestionRepository;
import com.sakda.chineselearning.repository.TelegramQuizSessionRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.service.AchievementService;
import com.sakda.chineselearning.telegram.message.QuizMessageBuilder;
import com.sakda.chineselearning.telegram.service.TelegramMessageService;
import com.sakda.chineselearning.telegram.service.TelegramQuizService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramQuizServiceImpl implements TelegramQuizService {

    private final UserRepository userRepository;
    private final TelegramQuizSessionRepository telegramQuizSessionRepository;
    private final QuestionRepository questionRepository;
    private final TelegramMessageService telegramMessageService;
    private final AchievementService achievementService;
    private final QuizMessageBuilder quizMessageBuilder;

    @Override
    public void sendRandomQuiz(String chatId) {

        List<Question> questions = questionRepository.findAll();

        sendQuizFromQuestions(
                chatId,
                questions,
                "No quiz questions available."
        );
    }

    @Override
    public void sendHskQuiz(String chatId, HskLevel level) {

        List<Question> questions =
                questionRepository.findByLesson_Level(level);

        sendQuizFromQuestions(
                chatId,
                questions,
                "No " + level + " quiz questions available."
        );
    }

    @Override
    @Transactional
    public void checkAnswer(String chatId, String answer) {

        User user = findUserByChatId(chatId);

        TelegramQuizSession session =
                telegramQuizSessionRepository
                        .findFirstByUserAndAnsweredFalseOrderByCreatedAtDesc(user)
                        .orElse(null);

        if (session == null) {
            telegramMessageService.sendMessage(
                    chatId,
                    "No active quiz. Type /quiz to start."
            );
            return;
        }

        boolean correct =
                answer != null
                && answer.trim().equalsIgnoreCase(session.getCorrectAnswer());

        session.setAnswered(true);
        telegramQuizSessionRepository.save(session);

        achievementService.checkQuizAchievements(user);

        Question question = session.getQuestion();

        if (question == null) {
            telegramMessageService.sendMessage(
                    chatId,
                    "This quiz question is no longer available. Type /quiz to try again."
            );
            return;
        }
        
        String correctAnswerText =
                findOptionTextByLetter(question, session.getCorrectAnswer());

        if (correct) {
            telegramMessageService.sendMessage(
            		chatId,
            		quizMessageBuilder.buildCorrectAnswerMessage(
            				question, 
            				session.getCorrectAnswer(),
            				correctAnswerText)
            );
        } else {
        	telegramMessageService.sendMessage(
                    chatId,
                    quizMessageBuilder.buildIncorrectAnswerMessage(
                            question,
                            session.getCorrectAnswer(),
                            correctAnswerText
                    )
            );
        }
    }

    private void sendQuizFromQuestions(
            String chatId,
            List<Question> questions,
            String emptyMessage
    ) {

        User user = findUserByChatId(chatId);

        if (telegramQuizSessionRepository
                .findFirstByUserAndAnsweredFalseOrderByCreatedAtDesc(user)
                .isPresent()) {

            telegramMessageService.sendMessage(
                    chatId,
                    "Please answer your current quiz first."
            );
            return;
        }

        if (questions.isEmpty()) {
            telegramMessageService.sendMessage(chatId, emptyMessage);
            return;
        }

        Random random = new Random();

        Question question =
                questions.get(random.nextInt(questions.size()));

        List<Option> options = question.getOptions();

        if (options == null || options.isEmpty()) {
            telegramMessageService.sendMessage(
                    chatId,
                    "This question has no options. Please try again later."
            );
            return;
        }

        char letter = 'A';
        String correctLetter = null;

        for (Option option : options) {

            if (option.getOptionText()
                    .equalsIgnoreCase(question.getCorrectAnswer())) {

                correctLetter = String.valueOf(letter);
            }

            letter++;
        }

        if (correctLetter == null) {
            telegramMessageService.sendMessage(
                    chatId,
                    "This question has no valid correct answer. Please try again later."
            );
            return;
        }

        TelegramQuizSession session = new TelegramQuizSession();

        session.setUser(user);
        session.setQuestion(question);
        session.setCorrectAnswer(correctLetter);
        session.setAnswered(false);

        telegramQuizSessionRepository.save(session);

        String message = quizMessageBuilder.buildQuizMessage(question, options);

        telegramMessageService.sendMessage(chatId, message);
    }

    private User findUserByChatId(String chatId) {

        return userRepository.findByTelegramChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with telegram chat id: " + chatId));
    }
    
    private String findOptionTextByLetter(Question question, String letter) {
    	
    	if (question.getOptions() == null || letter == null) {
    		return "";
    	}
    	
    	char targetLetter = letter.trim().toUpperCase().charAt(0);
    	char currentLetter = 'A';
    	
    	for (Option option : question.getOptions()) {
    		
    		if (currentLetter == targetLetter) {
    			return option.getOptionText();
    		}
    		
    		currentLetter++;
    	}
    	
    	return "";
    }
}