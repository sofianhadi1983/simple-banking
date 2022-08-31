package com.sofian.codingtest.websocket.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    private MessageType type;
    private String content;
    private String sender;
}
