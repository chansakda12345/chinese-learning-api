package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class LessonDTO {

	private Long id;
	
	private String title;
	
	private String description;
	
	private String thumbnailUrl;
}
