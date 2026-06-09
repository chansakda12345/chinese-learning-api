package com.sakda.chineselearning.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuizSubmitDTO {

    private Long lessonId;

    private List<AnswerDTO> answers;
}