package com.sakda.chineselearning.dto;

import java.util.List;

import lombok.Data;

@Data
public class LessonDetailDTO {
	
	private Long id;
	
	private String title;
	
	private String description;
	
	private List<WordDTO> words;

}
