package com.sakda.chineselearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
