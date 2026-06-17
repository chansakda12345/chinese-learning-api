package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.entity.Sentence;
import com.sakda.chineselearning.enums.HskLevel;

public interface SentenceRepository extends JpaRepository<Sentence, Long>{
	
	List<Sentence> findByLesson(Lesson lesson);
	
	Page<Sentence> findByLevel(HskLevel level, Pageable pageable);

    Page<Sentence> findByChineseContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Page<Sentence> findByLevelAndChineseContainingIgnoreCase(
            HskLevel level,
            String keyword,
            Pageable pageable
    );
}
