package com.sakda.chineselearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.StudentExamAnswer;

public interface StudentExamAnswerRepository extends JpaRepository<StudentExamAnswer, Long> {
	
}