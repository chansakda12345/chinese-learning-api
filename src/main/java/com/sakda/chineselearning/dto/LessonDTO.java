package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.HskLevel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LessonDTO {

	private Long id;
	
	private String title;
	
	private String description;
	
	@NotBlank(message = "Level is required")
	private HskLevel level;
	
	private String thumbnailUrl;
	
	private String content;
}
