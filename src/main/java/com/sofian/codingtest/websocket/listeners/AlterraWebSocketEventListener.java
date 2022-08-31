package com.sofian.codingtest.websocket.listeners;

import com.sofian.codingtest.websocket.dtos.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class AlterraWebSocketEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(AlterraWebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        LOG.info("Alterra menerima koneksi web socket baru "+event.toString());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) accessor.getSessionAttributes().get("username");

        if (username != null) {
            LOG.info("User disconnected : "+username);

            ChatMessageDTO chatMessage = new ChatMessageDTO();
            chatMessage.setSender(username);
            chatMessage.setType(ChatMessageDTO.MessageType.LEAVE);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
