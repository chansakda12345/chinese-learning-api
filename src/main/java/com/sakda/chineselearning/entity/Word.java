package com.sakda.chineselearning.entity;

import com.sakda.chineselearning.enums.HskLevel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "words")
public class Word {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String chinese;
	
	private String pinyin;
	
	private String english;
	
	private String khmer;
	
	@Enumerated(EnumType.STRING)
	private HskLevel level;
	
	private String audioUrl;
	
	@ManyToOne
	@JoinColumn(name = "lesson_id")
	private Lesson lesson;
	
	
}
