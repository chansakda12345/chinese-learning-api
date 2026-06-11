package com.sakda.chineselearning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.StudentProgress;

public interface StudentProgressRepository
        extends JpaRepository<StudentProgress, Long> {

    List<StudentProgress> findByUserEmail(String email);

    Optional<StudentProgress> findByUserEmailAndLessonId(
            String email,
            Long lessonId
    );
}