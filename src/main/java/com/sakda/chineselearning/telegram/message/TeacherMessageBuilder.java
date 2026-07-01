package com.sakda.chineselearning.telegram.message;

import org.springframework.stereotype.Component;

import com.sakda.chineselearning.dto.SentenceDTO;
import com.sakda.chineselearning.dto.WordDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeacherMessageBuilder {
	
	public String buildWordMessage(WordDTO word) {
    	
		return """
	            Chinese:
	            %s

	            Pinyin:
	            %s

	            English:
	            %s
	            """.formatted(
	                    word.getChinese(),
	                    word.getPinyin(),
	                    word.getEnglish()
	            );
    }
    
	public String buildSentenceMessage(SentenceDTO sentence) {
				
		return   """
	            Chinese:
	            %s

	            Pinyin:
	            %s

	            English:
	            %s
	            """.formatted(
	                    sentence.getChinese(),
	                    sentence.getPinyin(),
	                    sentence.getEnglish()
	    );
	}
}
