package com.omnicloud.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.WebSocketMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskWebSocketHandler implements WebSocketHandler {

    // teamId -> event stream
    private final Map<Long, Sinks.Many<String>> teamSinks =
            new ConcurrentHashMap<>();

    private Sinks.Many<String> getSink(Long teamId) {
        return teamSinks.computeIfAbsent(
                teamId,
                id -> Sinks.many().multicast().onBackpressureBuffer()
        );
    }

    // broadcast only to one team
    public void broadcast(Long teamId, String message) {
        getSink(teamId).tryEmitNext(message);
    }

    @Override
    public Flux<Void> handle(WebSocketSession session) {

        // client connects: ws://localhost:8080/ws/tasks?teamId=1
        String teamParam =
                session.getHandshakeInfo()
                        .getUri()
                        .getQuery()
                        .split("=")[1];

        Long teamId = Long.parseLong(teamParam);

        Flux<WebSocketMessage> output =
                getSink(teamId)
                        .asFlux()
                        .map(session::textMessage);

        return session.send(output);
    }
}