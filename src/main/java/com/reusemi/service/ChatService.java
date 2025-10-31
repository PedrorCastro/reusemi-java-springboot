package com.reusemi.service;

import com.reusemi.model.ChatMessage;
import com.reusemi.model.MessageEntity;
import com.reusemi.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Salva uma mensagem no banco
     */
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        MessageEntity entity = new MessageEntity();
        entity.setSender(chatMessage.getSender());
        entity.setContent(chatMessage.getContent());
        entity.setChatRoomId(chatMessage.getChatRoomId());
        entity.setTimestamp(LocalDateTime.now());

        messageRepository.save(entity);

        return chatMessage;
    }

    /**
     * Busca mensagens por sala de chat
     */
    public List<ChatMessage> getMessagesByRoom(String chatRoomId) {
        return messageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId)
                .stream()
                .map(entity -> new ChatMessage(
                        entity.getSender(),
                        entity.getContent(),
                        entity.getChatRoomId()
                ))
                .collect(Collectors.toList());
    }
}
