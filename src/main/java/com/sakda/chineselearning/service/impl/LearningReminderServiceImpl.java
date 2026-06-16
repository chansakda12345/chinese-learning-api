package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.LearningReminder;
import com.sakda.chineselearning.repository.LearningReminderRepository;
import com.sakda.chineselearning.service.LearningReminderService;
import com.sakda.chineselearning.service.TelegramService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LearningReminderServiceImpl implements LearningReminderService {	
	
	private final LearningReminderRepository learningReminderRepository;
	private final TelegramService telegramService;
	
	@Override
    public void processDueReminders() {

        List<LearningReminder> reminders =
                learningReminderRepository
                        .findBySentFalseAndRemindAtLessThanEqual(
                                LocalDateTime.now()
                        );
        
        for (LearningReminder reminder : reminders) {

            String message =
                    "📚 Time to review!\n"
                    + "Chinese: " + reminder.getWord().getChinese() + "\n"
                    + "Pinyin: " + reminder.getWord().getPinyin() + "\n"
                    + "English: " + reminder.getWord().getEnglish();

            telegramService.sendMessage(reminder.getUser().getTelegramChatId(), message);

            reminder.setSent(true);
            learningReminderRepository.save(reminder);
        }
    }

}
