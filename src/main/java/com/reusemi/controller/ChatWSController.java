package com.reusemi.controller;

import com.reusemi.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWSController {

    /**
     * Recebe mensagens enviadas pelos clientes (via STOMP)
     * e as distribui para todos os inscritos no tópico.
     */
    @MessageMapping("/chat.sendMessage")   // cliente envia para /app/chat.sendMessage
    @SendTo("/topic/messages")             // todos que ouvem /topic/messages recebem
    public ChatMessage sendMessage(ChatMessage message) {
        // Aqui você poderia salvar no banco antes de retornar
        return message;
    }
}
