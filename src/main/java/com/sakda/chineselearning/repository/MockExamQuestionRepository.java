package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.MockExamQuestion;

public interface MockExamQuestionRepository extends JpaRepository<MockExamQuestion, Long> {
	
	List<MockExamQuestion> findByMockExamId(Long mockExamId);
	
}
