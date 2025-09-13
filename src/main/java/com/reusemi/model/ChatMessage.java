package com.reusemi.model;

public class ChatMessage {
    private String sender;
    private String content;
    private String chatRoomId;

    public ChatMessage() {}

    public ChatMessage(String sender, String content, String chatRoomId) {
        this.sender = sender;
        this.content = content;
        this.chatRoomId = chatRoomId;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }
    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
