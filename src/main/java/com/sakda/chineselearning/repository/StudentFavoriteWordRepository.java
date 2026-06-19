package com.sakda.chineselearning.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.Word;

public interface StudentFavoriteWordRepository extends JpaRepository<StudentFavoriteWord, Long>{
	
	boolean existsByUserAndWord(User user, Word word);
	
	List<StudentFavoriteWord> findByUser(User user);
	
	List<StudentFavoriteWord> findByUserAndNextReviewAtLessThanEqual(User user, LocalDateTime now);
	
	long countByUser(User user);
	
	long countByUserAndLastReviewedAtIsNull(User user);
	
	long countByUserAndNextReviewAtLessThanEqual(User user, LocalDateTime now);
	
	long countByUserAndLastReviewedAtBetween(User user, LocalDateTime start, LocalDateTime end);
	
	List<StudentFavoriteWord> findByUserAndLastReviewedAtIsNotNullOrderByLastReviewedAtDesc(
	        User user
	);

}
