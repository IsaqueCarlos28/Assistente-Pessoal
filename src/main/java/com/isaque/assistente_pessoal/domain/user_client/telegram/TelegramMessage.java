package com.isaque.assistente_pessoal.domain.user_client.telegram;


import com.fasterxml.jackson.annotation.JsonProperty;

public record TelegramMessage(
        @JsonProperty("message_id") Long messageId,
        @JsonProperty("chat") TelegramChat chat,
        @JsonProperty("text") String text
) {
}