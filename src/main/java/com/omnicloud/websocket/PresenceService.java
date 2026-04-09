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
    private final Set<String> online = ConcurrentHashMap.newKeySet();

    public PresenceService(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    public void connected(String username) {
        online.add(username);
        broadcast(username, PresenceEvent.Type.CONNECTED);
    }

    public void disconnected(String username) {
        online.remove(username);
        broadcast(username, PresenceEvent.Type.DISCONNECTED);
    }

    // this is what CollabController calls
    public void userTyping(String username) {
        broadcast(username, PresenceEvent.Type.TYPING);
    }

    public Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(online);
    }

    private void broadcast(String username, PresenceEvent.Type type) {
        messaging.convertAndSend("/topic/presence",
                PresenceEvent.builder()
                        .username(username)
                        .type(type)
                        .build());
    }
}