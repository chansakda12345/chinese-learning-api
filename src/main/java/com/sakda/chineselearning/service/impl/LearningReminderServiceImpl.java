package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.entity.LearningReminder;
import com.sakda.chineselearning.enums.ReminderType;
import com.sakda.chineselearning.repository.LearningReminderRepository;
import com.sakda.chineselearning.service.LearningReminderService;
import com.sakda.chineselearning.service.TelegramService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LearningReminderServiceImpl implements LearningReminderService {	
	
	private final LearningReminderRepository learningReminderRepository;
	private final TelegramService telegramService;
	
	@Transactional
	@Override
    public void processDueReminders() {

        List<LearningReminder> reminders =
                learningReminderRepository
                        .findBySentFalseAndRemindAtLessThanEqual(
                                LocalDateTime.now()
                        );
        
        for (LearningReminder reminder : reminders) {
        	
        	String message;
        	
        	if (reminder.getType() == ReminderType.WORD) {
        		
        		message = 
        				"📚 Word Review Time!\n"
        				+ "Chinese: " + reminder.getWord().getChinese() + "\n"
        				+ "Pinyin: " + reminder.getWord().getPinyin() + "\n"
        				+ "English: " + reminder.getWord().getEnglish();
        	} else if (reminder.getType() == ReminderType.SENTENCE) {
        		
        		message =
        	            "📚 Sentence Review Time!\n"
        	            + "Chinese: " + reminder.getSentence().getChinese() + "\n"
        	            + "Pinyin: " + reminder.getSentence().getPinyin() + "\n"
        	            + "English: " + reminder.getSentence().getEnglish();
        	} else {
        	    continue;
        	}

            telegramService.sendMessage(reminder.getUser().getTelegramChatId(), message);

            reminder.setSent(true);
            learningReminderRepository.save(reminder);
        }
    }

}
