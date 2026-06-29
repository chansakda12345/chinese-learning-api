package com.sakda.chineselearning.dto;

import com.sakda.chineselearning.enums.HskLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Level is required")
    private HskLevel level;

    private String thumbnailUrl;

    @NotBlank(message = "Content is required")
    private String content;
}