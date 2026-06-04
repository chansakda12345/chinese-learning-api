package com.sakda.chineselearning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakda.chineselearning.entity.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>{
	
	Page<Word> findByChineseContainingIgnoreCaseOrPinyinContainingIgnoreCaseOrEnglishContainingIgnoreCaseOrKhmerContainingIgnoreCase(
            String chinese,
            String pinyin,
            String english,
            String khmer,
            Pageable pageable
    );
}
