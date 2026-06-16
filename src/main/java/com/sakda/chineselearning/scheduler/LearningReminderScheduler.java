package com.sakda.chineselearning.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sakda.chineselearning.service.LearningReminderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LearningReminderScheduler {

    private final LearningReminderService learningReminderService;

    @Scheduled(fixedRate = 60000)
    public void processDueReminders() {
    	
    	learningReminderService.processDueReminders();
    }
 }