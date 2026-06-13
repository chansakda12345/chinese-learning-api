package com.sakda.chineselearning.dto;

import java.util.List;

import com.sakda.chineselearning.enums.HskLevel;

import lombok.Data;

@Data
public class LessonDetailDTO {
	
	private Long id;
	
	private String title;
	
	private String description;
	
	private HskLevel level;
	
	private List<WordDTO> words;
	
	private String thumbnailUrl;
	
	private String content;

}
