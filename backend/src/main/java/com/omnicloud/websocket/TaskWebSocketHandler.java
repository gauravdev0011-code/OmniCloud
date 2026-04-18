package com.omnicloud.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TaskWebSocketHandler implements WebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        sessions.forEach(s -> {
            try {
                s.sendMessage(message);
            } catch (Exception ignored) {}
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}