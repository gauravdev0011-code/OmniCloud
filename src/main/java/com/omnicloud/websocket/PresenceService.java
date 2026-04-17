package com.omnicloud.websocket;

import com.omnicloud.realtime.PresenceEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceService {

    private final SimpMessagingTemplate messaging;
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public PresenceService(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    public void connected(String username) {
        if (username == null || username.isBlank()) return;

        onlineUsers.add(username);
        broadcast(username, PresenceEvent.Type.CONNECTED);
    }

    public void disconnected(String username) {
        if (username == null || username.isBlank()) return;

        onlineUsers.remove(username);
        broadcast(username, PresenceEvent.Type.DISCONNECTED);
    }

    public void userTyping(String username) {
        if (username == null || username.isBlank()) return;

        broadcast(username, PresenceEvent.Type.TYPING);
    }

    public Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }

    private void broadcast(String username, PresenceEvent.Type type) {
        messaging.convertAndSend("/topic/presence",
                PresenceEvent.builder()
                        .username(username)
                        .type(type)
                        .build());
    }
}