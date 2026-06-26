package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.StudentExamAttempt;
import com.sakda.chineselearning.entity.User;

public interface StudentExamAttemptRepository extends JpaRepository<StudentExamAttempt, Long> {

	List<StudentExamAttempt> findByUserOrderByStartedAtDesc(User user);
	
	List<StudentExamAttempt> findByUser(User user);
}