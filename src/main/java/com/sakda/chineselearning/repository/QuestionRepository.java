package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Question;
import com.sakda.chineselearning.enums.HskLevel;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	List<Question> findByLesson_Level(HskLevel level);

}
