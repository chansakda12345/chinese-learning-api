package com.sakda.chineselearning.telegram.service;

import com.sakda.chineselearning.telegram.entity.TelegramPost;

public interface TelegramBroadcastService {
	
	void broadcastPost(TelegramPost post);
	
	void broadcastPostById(Long postId);

}
