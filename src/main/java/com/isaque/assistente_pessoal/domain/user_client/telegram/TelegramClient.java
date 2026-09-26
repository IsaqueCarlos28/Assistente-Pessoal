package com.isaque.assistente_pessoal.domain.user_client.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TelegramClient {

    private final RestClient restClient;

    public TelegramClient(RestClient.Builder builder,
                          @Value("${telegram.bot.token}") String botToken) {
        this.restClient = builder
                .baseUrl("https://api.telegram.org/bot" + botToken)
                .build();
    }

    public void sendMessage(Long chatId, String text) {
        restClient.post()
                .uri("/sendMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SendMessageRequest(chatId, text))
                .retrieve()
                .toBodilessEntity();
    }

    public void setWebhook(String url) {
        restClient.post()
                .uri("/setWebhook")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SetWebhookRequest(url))
                .retrieve()
                .toBodilessEntity();
    }

    private record SendMessageRequest(
            @JsonProperty("chat_id") Long chatId,
            @JsonProperty("text") String text) {
    }

    private record SetWebhookRequest(
            @JsonProperty("url") String url) {
    }
}