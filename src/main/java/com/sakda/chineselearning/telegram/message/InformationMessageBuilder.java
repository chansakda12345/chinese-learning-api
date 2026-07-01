package com.sakda.chineselearning.telegram.message;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InformationMessageBuilder {
		
	public String buildStartMessage() {
		
        return """
                Welcome to Learn Chinese!

                /word
				/sentence
				/review
				/quiz
				/hsk1quiz
				/hsk2quiz
				/hsk3quiz
				/help
                """;
	}
	
    public String buildHelpMessage() {
        return """
                Available Commands:

                /word
                Get random word

                /sentence
                Get random sentence

                /review
                Get review due items

                /quiz
                Get random quiz question

                /hsk1quiz
                Get random HSK1 quiz

                /hsk2quiz
                Get random HSK2 quiz

                /hsk3quiz
                Get random HSK3 quiz

                /help
                Show commands
                """;
    }
    
    public String buildUnknownCommandMessage() {
        return """
                ❓ Unknown command.

                Type /help to see available commands.
                """;
    }
}
