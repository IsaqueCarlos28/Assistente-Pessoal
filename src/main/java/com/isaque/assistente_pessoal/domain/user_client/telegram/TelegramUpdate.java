package com.isaque.assistente_pessoal.domain.user_client.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TelegramUpdate(
        @JsonProperty("update_id") Long updateId,
        @JsonProperty("message") TelegramMessage message
) {
}
