package com.sakda.chineselearning.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.enums.HskLevel;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
	
	Page<Lesson> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<Lesson> findByLevel(HskLevel level, Pageable pageable);
	
	Page<Lesson> findByLevelAndTitleContainingIgnoreCase(HskLevel level, String keyword, Pageable pageable);

}
