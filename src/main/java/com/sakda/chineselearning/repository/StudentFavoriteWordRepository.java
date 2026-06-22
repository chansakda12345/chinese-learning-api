package com.sakda.chineselearning.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.Word;
import com.sakda.chineselearning.enums.HskLevel;

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
	
	long countByUserAndWord_LevelAndReviewCountGreaterThan(User user, HskLevel level, Integer reviewCount);
	
	@Query("SELECT COALESCE(SUM(f.reviewCount), 0) FROM StudentFavoriteWord f WHERE f.user = :user")
	Long sumReviewCountByUser(@Param("user") User user);

}
