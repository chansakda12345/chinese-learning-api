package com.sakda.chineselearning.dto;

import lombok.Data;

@Data
public class QuestionDTO {

    private Long id;

    private String questionText;

    private String correctAnswer;
}