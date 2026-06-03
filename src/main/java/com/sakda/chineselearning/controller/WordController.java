package com.sakda.chineselearning.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakda.chineselearning.dto.WordDTO;
import com.sakda.chineselearning.service.WordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
public class WordController {
	
	private final WordService wordService;
	
	@PostMapping
	public WordDTO create(@RequestBody WordDTO wordDTO) {
		return wordService.create(wordDTO);
	}
	
	@GetMapping
	public List<WordDTO> getAll() {
		return wordService.getAll();
	}
	
	@GetMapping("/{id}")
	public WordDTO getById(@PathVariable Long id) {
		return wordService.getById(id);
	}
	
	@PutMapping("/{id}")
	public WordDTO updateById(@PathVariable Long id, @RequestBody WordDTO wordDTO) {
		return wordService.update(id, wordDTO);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	    wordService.delete(id);
	}

}
