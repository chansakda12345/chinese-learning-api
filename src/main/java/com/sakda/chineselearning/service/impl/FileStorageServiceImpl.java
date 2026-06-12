package com.sakda.chineselearning.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sakda.chineselearning.exception.BusinessException;
import com.sakda.chineselearning.service.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{
		
	@Value("${app.upload.audio-dir}")
	private String audioDir;
	
	@Value("${app.upload.image-dir}")
	private String imageDir;

	@Override
	public String uploadAudio(MultipartFile file) {
		
		if (file.isEmpty()) {
			throw new BusinessException("File cannot be empty");
		}
		
		String fileName = file.getOriginalFilename();
		
		if (fileName == null) {
			
			throw new BusinessException("File name cannot be null");
		}
		
		if (!(fileName.endsWith(".mp3") || (fileName.endsWith(".wav") || (fileName.endsWith(".m4a"))))) {
			
			throw new BusinessException("Only mp3, wav, and m4a files are allowed");
		}
		
		try {
			
			Path uploadPath = Paths.get(audioDir);
			
			Files.createDirectories(uploadPath);
						
			String uniqueFileName = System.currentTimeMillis() + "-" + fileName;
			
			Path filePath = uploadPath.resolve(uniqueFileName);
			
			Files.copy(file.getInputStream(), filePath);
			
			return filePath.toString();
			
		} catch (Exception e) {
			throw new BusinessException("Could not upload audio file");
		}
		
	}

	@Override
	public String uploadImage(MultipartFile file) {
		
		if (file.isEmpty()) {
			throw new BusinessException("File cannot be empty");
		}
		
		String fileName = file.getOriginalFilename();
		
		if (fileName == null) {
			
			throw new BusinessException("File name cannot be null");
		}
		
		if (!(fileName.endsWith(".jpg") || (fileName.endsWith(".jpeg") || (fileName.endsWith(".png")) || (fileName.endsWith(".webp"))))) {
			
			throw new BusinessException("Only jpg, jpeg, png, and webp files are allowed");
		}
		
		try {
			
			Path uploadPath = Paths.get(imageDir);
			
			Files.createDirectories(uploadPath);
						
			String uniqueFileName = System.currentTimeMillis() + "-" + fileName;
			
			Path filePath = uploadPath.resolve(uniqueFileName);
			
			Files.copy(file.getInputStream(), filePath);
			
			return filePath.toString();
			
		} catch (Exception e) {
			throw new BusinessException("Could not upload image file");
		}
	}

}
