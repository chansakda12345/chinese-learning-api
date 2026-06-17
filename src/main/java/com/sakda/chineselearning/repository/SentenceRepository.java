package com.sakda.chineselearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Lesson;
import com.sakda.chineselearning.entity.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Long>{
	
	List<Sentence> findByLesson(Lesson lesson);

}
