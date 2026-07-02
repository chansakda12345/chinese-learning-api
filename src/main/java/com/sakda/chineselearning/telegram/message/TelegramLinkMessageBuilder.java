package com.sakda.chineselearning.telegram.message;

import org.springframework.stereotype.Component;

@Component
public class TelegramLinkMessageBuilder {

    public String buildLinkSuccessMessage() {
        return """
                ✅ Telegram account linked successfully.

                You can now use Telegram learning features.
                """;
    }

    public String buildInvalidCodeMessage() {
        return """
                ❌ Invalid verification code.

                Please generate a new code from the website.
                """;
    }

    public String buildCodeExpiredMessage() {
        return """
                ⏰ Verification code expired.

                Please generate a new code from the website.
                """;
    }

    public String buildCodeAlreadyUsedMessage() {
        return """
                ❌ This verification code was already used.

                Please generate a new code from the website.
                """;
    }

    public String buildAlreadyLinkedMessage() {
        return """
                ✅ Your website account is already linked to Telegram.
                """;
    }

    public String buildTelegramAlreadyLinkedMessage() {
        return """
                ❌ This Telegram account is already linked to another website account.
                """;
    }
}