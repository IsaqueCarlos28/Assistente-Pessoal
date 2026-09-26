package com.isaque.assistente_pessoal.domain.user_client.telegram;

import com.isaque.assistente_pessoal.service.MensagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/telegram")
public class TelegramWebhookController {

    private final MensagemService mensagemService;
    private final TelegramClient telegramClient;

    public TelegramWebhookController(MensagemService mensagemService, TelegramClient telegramClient) {
        this.mensagemService = mensagemService;
        this.telegramClient = telegramClient;
    }

    @PostMapping
    public ResponseEntity<Void> receberUpdate(@RequestBody TelegramUpdate update) {
        // Ignora updates que não são mensagem de texto (ex.: edição de
        // mensagem, callback de botão) — ainda não tratamos esses casos.
        if (update.message() == null || update.message().text() == null) {
            return ResponseEntity.ok().build();
        }

        Long chatId = update.message().chat().id();
        String texto = update.message().text();

        String resposta = mensagemService.processarMensagemRecebida(String.valueOf(chatId), texto);

        telegramClient.sendMessage(chatId, resposta);

        return ResponseEntity.ok().build();
    }
}