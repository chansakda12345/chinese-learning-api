package com.sakda.chineselearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sakda.chineselearning.dto.StudentFavoriteWordDTO;
import com.sakda.chineselearning.entity.StudentFavoriteWord;
import com.sakda.chineselearning.entity.User;
import com.sakda.chineselearning.entity.Word;
import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.exception.ResourceNotFoundException;
import com.sakda.chineselearning.mapper.StudentFavoriteWordMapper;
import com.sakda.chineselearning.repository.StudentFavoriteWordRepository;
import com.sakda.chineselearning.repository.UserRepository;
import com.sakda.chineselearning.repository.WordRepository;
import com.sakda.chineselearning.service.StudentFavoriteWordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentFavoriteWordServiceImpl implements StudentFavoriteWordService {

	
	private final StudentFavoriteWordRepository studentFavoriteWordRepository;
	private final WordRepository wordRepository;
	private final UserRepository userRepository;
	private final StudentFavoriteWordMapper studentFavoriteWordMapper;
	
	@Override
	public void saveFavoriteWord(Long wordId) {
		
		User user = getCurrentUser();
		
		Word word = wordRepository.findById(wordId)
				.orElseThrow(() -> new ResourceNotFoundException("Word not found with id: " + wordId));
		
		if (studentFavoriteWordRepository.existsByUserAndWord(user, word)) {
			return;
		}
		
		StudentFavoriteWord favorite = new StudentFavoriteWord();
		
		favorite.setUser(user);
		favorite.setWord(word);
		favorite.setSavedAt(LocalDateTime.now());
		
		studentFavoriteWordRepository.save(favorite);
		
	}

	@Override
	public List<StudentFavoriteWordDTO> getMyFavoriteWords() {
		
		User user = getCurrentUser();
		
		return studentFavoriteWordRepository.findByUser(user)
				.stream()
				.map(studentFavoriteWordMapper::toDTO)
				.toList();
	}

	@Override
	public void removeFavoriteWord(Long favoriteId) {
		
		User user = getCurrentUser();
		
		StudentFavoriteWord favoriteWord = studentFavoriteWordRepository.findById(favoriteId)
			.orElseThrow(() -> new ResourceNotFoundException("Favorite word not found with id: " + favoriteId));
		
		if(!favoriteWord.getUser().getId().equals(user.getId())) {
			throw new BusinessException("Favorite word does not belong to current user");
		}
		
		studentFavoriteWordRepository.delete(favoriteWord);
	}
	
	private User getCurrentUser() {
	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("User not found"));
	}

}
