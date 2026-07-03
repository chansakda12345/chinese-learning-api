package com.sakda.chineselearning.telegram.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sakda.chineselearning.telegram.dto.TelegramPostDTO;
import com.sakda.chineselearning.telegram.entity.TelegramPost;
import com.sakda.chineselearning.telegram.mapper.TelegramPostMapper;
import com.sakda.chineselearning.telegram.repository.TelegramPostRepository;
import com.sakda.chineselearning.telegram.service.TelegramBroadcastService;
import com.sakda.chineselearning.telegram.service.TelegramPostService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramPostServiceImpl implements TelegramPostService{
	
	private final TelegramPostRepository telegramPostRepository;
	private final TelegramPostMapper telegramPostMapper;
	private final TelegramBroadcastService telegramBroadcastService;
	
	@Override
	@Transactional
	public TelegramPostDTO createPost(TelegramPostDTO dto) {
		
		TelegramPost post = telegramPostMapper.toEntity(dto);
		
		TelegramPost savedPost = telegramPostRepository.save(post);
		
		return telegramPostMapper.toDTO(savedPost);
	}

	@Override
	public List<TelegramPostDTO> getAllPosts() {
		
		List<TelegramPost> posts = telegramPostRepository.findAll();
		
		return telegramPostMapper.toDTOList(posts);
	}

	@Override
	public void sendPost(Long id) {
		
		telegramBroadcastService.broadcastPostById(id);
	}

}
