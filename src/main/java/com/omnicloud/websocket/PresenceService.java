package com.omnicloud.websocket;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceService {

    // teamId -> online users
    private final Map<Long, Set<Long>> teamPresence =
            new ConcurrentHashMap<>();

    public void userJoined(Long teamId, Long userId) {
        teamPresence
                .computeIfAbsent(teamId,
                        id -> ConcurrentHashMap.newKeySet())
                .add(userId);
    }

    public void userLeft(Long teamId, Long userId) {
        Set<Long> users = teamPresence.get(teamId);
        if (users != null) {
            users.remove(userId);
        }
    }

    public Set<Long> getOnlineUsers(Long teamId) {
        return teamPresence.getOrDefault(teamId, Set.of());
    }
}