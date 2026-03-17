package com.omnicloud.websocket;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskEventService {

    // One sink per team
    private final Map<Long, Sinks.Many<String>> teamSinks = new ConcurrentHashMap<>();

    // Get sink for team, create if not exists
    public Sinks.Many<String> getSink(Long teamId) {
        return teamSinks.computeIfAbsent(teamId,
                id -> Sinks.many().multicast().directBestEffort());
    }

    // Broadcast message to specific team
    public void broadcast(Long teamId, String message) {
        getSink(teamId).tryEmitNext(message);
    }
}