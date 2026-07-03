package com.sakda.chineselearning.telegram.service;

import java.util.List;

import com.sakda.chineselearning.telegram.dto.TelegramPostDTO;

public interface TelegramPostService {
	
	TelegramPostDTO createPost(TelegramPostDTO dto);
	
	List<TelegramPostDTO> getAllPosts();
	
	void sendPost(Long id);

}
