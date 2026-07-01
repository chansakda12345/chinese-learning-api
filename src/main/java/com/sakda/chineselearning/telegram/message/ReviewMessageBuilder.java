package com.sakda.chineselearning.telegram.message;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sakda.chineselearning.entity.StudentFavoriteSentence;
import com.sakda.chineselearning.entity.StudentFavoriteWord;

@Component
public class ReviewMessageBuilder {

    public String buildReviewMessage(
            List<StudentFavoriteWord> dueWords,
            List<StudentFavoriteSentence> dueSentences
    ) {

        if (dueWords.isEmpty() && dueSentences.isEmpty()) {
            return """
                    🎉 Great job!

                    No items due for review.
                    """;
        }

        StringBuilder message = new StringBuilder("📚 Review Due\n\n");

        if (!dueWords.isEmpty()) {
            message.append("Words:\n");

            dueWords.forEach(word ->
                    message.append("• ")
                            .append(word.getWord().getChinese())
                            .append("\n")
            );

            message.append("\n");
        }

        if (!dueSentences.isEmpty()) {
            message.append("Sentences:\n");

            dueSentences.forEach(sentence ->
                    message.append("• ")
                            .append(sentence.getSentence().getChinese())
                            .append("\n")
            );
        }

        return message.toString();
    }
}