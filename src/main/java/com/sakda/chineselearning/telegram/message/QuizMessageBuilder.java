package com.sakda.chineselearning.telegram.message;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sakda.chineselearning.entity.Option;
import com.sakda.chineselearning.entity.Question;

@Component
public class QuizMessageBuilder {
	
	public String buildQuizMessage(Question question, List<Option> options) {
		
		StringBuilder message = new StringBuilder();
		
		message.append("📝 Quiz Time!\n\n");
		message.append("Question:\n");
		message.append(question.getQuestionText()).append("\n\n");
		
		char letter = 'A';
		
		for (Option option : options) {
			
			message.append(letter)
					.append(". ")
					.append(option.getOptionText())
					.append("\n");
			
			letter++;
		}
		
		message.append("\nReply with A, B, C, or D");
		
		return message.toString();
	}
	
	public String buildCorrectAnswerMessage(
	        Question question,
	        String correctAnswer,
	        String correctAnswerText
	) {
	    return """
	            ✅ Correct!

	            Question:
	            %s

	            Correct Answer:
	            %s. %s
	            """
	            .formatted(
	                    question.getQuestionText(),
	                    correctAnswer,
	                    correctAnswerText
	            );
	}

	public String buildIncorrectAnswerMessage(
	        Question question,
	        String correctAnswer,
	        String correctAnswerText
	) {
	    return """
	            ❌ Incorrect

	            Question:
	            %s

	            Correct Answer:
	            %s. %s
	            """
	            .formatted(
	                    question.getQuestionText(),
	                    correctAnswer,
	                    correctAnswerText
	            );
	}

}
