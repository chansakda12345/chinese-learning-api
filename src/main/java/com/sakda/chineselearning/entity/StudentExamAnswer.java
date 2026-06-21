package com.sakda.chineselearning.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "student_exam_answers")
@Data
public class StudentExamAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String selectedOption;

    private Boolean correct;

    @ManyToOne
    @JoinColumn(name = "attempt_id")
    private StudentExamAttempt attempt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}