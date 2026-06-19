package com.sakda.chineselearning.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.Sentence;
import com.sakda.chineselearning.entity.StudentFavoriteSentence;
import com.sakda.chineselearning.entity.User;

public interface StudentFavoriteSentenceRepository extends JpaRepository<StudentFavoriteSentence, Long>{
	
	boolean existsByUserAndSentence(User user, Sentence sentence);
	
	List<StudentFavoriteSentence> findByUser(User user);
	
	List<StudentFavoriteSentence> findByUserAndNextReviewAtLessThanEqual(
            User user,
            LocalDateTime now
    );
	
	long countByUserAndNextReviewAtLessThanEqual(User user, LocalDateTime now);
	
	long countByUser(User user);
	
	long countByUserAndLastReviewedAtIsNull(User user);
	
	long countByUserAndLastReviewedAtBetween(User user, LocalDateTime start, LocalDateTime end);
	
	List<StudentFavoriteSentence> findByUserAndLastReviewedAtIsNotNullOrderByLastReviewedAtDesc(
	        User user
	);

}
