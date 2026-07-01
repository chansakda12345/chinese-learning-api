package com.sakda.chineselearning.telegram.service.impl;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.service.SentenceService;
import com.sakda.chineselearning.service.TelegramMessageService;
import com.sakda.chineselearning.service.WordService;
import com.sakda.chineselearning.telegram.message.TeacherMessageBuilder;
import com.sakda.chineselearning.telegram.service.TelegramTeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramTeacherServiceImpl implements TelegramTeacherService {

    private final WordService wordService;
    private final SentenceService sentenceService;

    private final TeacherMessageBuilder teacherMessageBuilder;
    private final TelegramMessageService telegramMessageService;

    @Override
    public void teachRandomWord(String chatId) {

        WordDTO word = wordService.getRandomWord();

        telegramMessageService.sendMessage(
                chatId,
                teacherMessageBuilder.buildWordMessage(word)
        );
    }

    @Override
    public void teachRandomSentence(String chatId) {

        SentenceDTO sentence = sentenceService.getRandomSentence();

        telegramMessageService.sendMessage(
                chatId,
                teacherMessageBuilder.buildSentenceMessage(sentence)
        );
    }
}
