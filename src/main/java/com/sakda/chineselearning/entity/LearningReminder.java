package com.sakda.chineselearning.entity;

import java.time.LocalDateTime;

import com.sakda.chineselearning.enums.ReminderType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "learning_reminders")
@Data
public class LearningReminder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime remindAt;
	
	private boolean sent;
	
	@Enumerated(EnumType.STRING)
	private ReminderType type;
	
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "word_id")
	private Word word;
	
	@ManyToOne
	@JoinColumn(name = "sentence_id")
	private Sentence sentence;

}
