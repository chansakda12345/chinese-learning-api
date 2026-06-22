package com.sakda.chineselearning.service.impl;

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
import com.sakda.chineselearning.service.TelegramQuizService;
import com.sakda.chineselearning.service.TelegramService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramQuizServiceImpl implements TelegramQuizService {

    private final UserRepository userRepository;
    private final TelegramQuizSessionRepository telegramQuizSessionRepository;
    private final QuestionRepository questionRepository;
    private final TelegramService telegramService;

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
    public void checkAnswer(String chatId, String answer) {

        User user = findUserByChatId(chatId);

        TelegramQuizSession session =
                telegramQuizSessionRepository
                        .findFirstByUserAndAnsweredFalseOrderByCreatedAtDesc(user)
                        .orElse(null);

        if (session == null) {
            telegramService.sendMessage(
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

        Question question = session.getQuestion();

        if (question == null) {
            telegramService.sendMessage(
                    chatId,
                    "This quiz question is no longer available. Type /quiz to try again."
            );
            return;
        }

        if (correct) {
            telegramService.sendMessage(
                    chatId,
                    """
                    ✅ Correct!

                    Question:
                    %s

                    Correct Answer:
                    %s
                    """
                    .formatted(
                            question.getQuestionText(),
                            session.getCorrectAnswer()
                    )
            );
        } else {
            telegramService.sendMessage(
                    chatId,
                    """
                    ❌ Incorrect

                    Correct Answer:
                    %s

                    Question:
                    %s
                    """
                    .formatted(
                            session.getCorrectAnswer(),
                            question.getQuestionText()
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

            telegramService.sendMessage(
                    chatId,
                    "Please answer your current quiz first."
            );
            return;
        }

        if (questions.isEmpty()) {
            telegramService.sendMessage(chatId, emptyMessage);
            return;
        }

        Random random = new Random();

        Question question =
                questions.get(random.nextInt(questions.size()));

        List<Option> options = question.getOptions();

        if (options == null || options.isEmpty()) {
            telegramService.sendMessage(
                    chatId,
                    "This question has no options. Please try again later."
            );
            return;
        }

        StringBuilder message = new StringBuilder();

        message.append("📝 Quiz Time!\n\n");
        message.append("Question:\n");
        message.append(question.getQuestionText()).append("\n\n");

        char letter = 'A';
        String correctLetter = null;

        for (Option option : options) {

            message.append(letter)
                    .append(". ")
                    .append(option.getOptionText())
                    .append("\n");

            if (option.getOptionText()
                    .equalsIgnoreCase(question.getCorrectAnswer())) {

                correctLetter = String.valueOf(letter);
            }

            letter++;
        }

        if (correctLetter == null) {
            telegramService.sendMessage(
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

        message.append("\nReply with A, B, C, or D");

        telegramService.sendMessage(chatId, message.toString());
    }

    private User findUserByChatId(String chatId) {

        return userRepository.findByTelegramChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with telegram chat id: " + chatId));
    }
}