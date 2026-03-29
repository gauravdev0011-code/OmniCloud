package com.omnicloud.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnicloud.realtime.PresenceEvent;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskWebSocketHandler implements WebSocketHandler {

    private final Map<Long, Sinks.Many<String>> teamSinks =
            new ConcurrentHashMap<>();

    private final PresenceService presenceService;
    private final ObjectMapper mapper = new ObjectMapper();

    public TaskWebSocketHandler(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    private Sinks.Many<String> getSink(Long teamId) {
        return teamSinks.computeIfAbsent(
                teamId,
                id -> Sinks.many().multicast().onBackpressureBuffer()
        );
    }

    public void broadcast(Long teamId, String message) {
        getSink(teamId).tryEmitNext(message);
    }

    @Override
    public Flux<Void> handle(WebSocketSession session) {

        // ws://localhost:8080/ws/tasks?teamId=1&userId=10
        String query = session.getHandshakeInfo().getUri().getQuery();

        Long teamId =
                Long.parseLong(query.split("&")[0].split("=")[1]);

        Long userId =
                Long.parseLong(query.split("&")[1].split("=")[1]);

        presenceService.userJoined(teamId, userId);

        sendPresence("JOIN", teamId, userId);

        Flux<WebSocketMessage> output =
                getSink(teamId)
                        .asFlux()
                        .map(session::textMessage);

        return session.send(output)
                .doFinally(signal -> {
                    presenceService.userLeft(teamId, userId);
                    sendPresence("LEAVE", teamId, userId);
                });
    }

    private void sendPresence(String type,
                              Long teamId,
                              Long userId) {

        try {
            PresenceEvent event =
                    new PresenceEvent(type, userId, teamId);

            String json = mapper.writeValueAsString(event);

            broadcast(teamId, json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}