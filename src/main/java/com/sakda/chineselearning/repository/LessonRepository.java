package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.enums.HskLevel;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
	
	List<Lesson> findByLevel(HskLevel level);

}
