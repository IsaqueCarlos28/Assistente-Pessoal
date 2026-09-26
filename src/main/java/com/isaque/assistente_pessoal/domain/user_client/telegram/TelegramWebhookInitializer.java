package com.isaque.assistente_pessoal.domain.user_client.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TelegramWebhookInitializer implements ApplicationRunner {

    private final TelegramClient telegramClient;
    private final String webhookUrl;

    public TelegramWebhookInitializer(TelegramClient telegramClient,
                                      @Value("${telegram.webhook.url}") String webhookUrl) {
        this.telegramClient = telegramClient;
        this.webhookUrl = webhookUrl;
    }

    @Override
    public void run(ApplicationArguments args) {
        telegramClient.setWebhook(webhookUrl);
    }
}