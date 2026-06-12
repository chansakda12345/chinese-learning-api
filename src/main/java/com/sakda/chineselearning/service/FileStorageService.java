package com.sakda.chineselearning.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String uploadAudio(MultipartFile file);
	
	String uploadImage(MultipartFile file);
}
