package com.sakda.chineselearning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sakda.chineselearning.entity.StudentProgress;
import com.sakda.chineselearning.entity.User;

public interface StudentProgressRepository
        extends JpaRepository<StudentProgress, Long> {

    List<StudentProgress> findByUserEmail(String email);

    Optional<StudentProgress> findByUserEmailAndLessonId(
            String email,
            Long lessonId
    );
    
    @Query("""
    	    SELECT COALESCE(SUM(p.score), 0)
    	    FROM StudentProgress p
    	    WHERE p.user = :user
    	""")
    Integer sumScoreByUser(@Param("user") User user);
}