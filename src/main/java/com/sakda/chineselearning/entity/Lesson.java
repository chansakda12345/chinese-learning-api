package com.sakda.chineselearning.entity;

import java.util.List;

import com.sakda.chineselearning.enums.HskLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "lessons")
@Data
public class Lesson {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private String thumbnailUrl;
	
	@Enumerated(EnumType.STRING)
	private HskLevel level;
	
	@OneToMany(mappedBy = "lesson")
	private List<Word> words;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@OneToMany(mappedBy = "lesson")
	private List<Question> questions;

}
