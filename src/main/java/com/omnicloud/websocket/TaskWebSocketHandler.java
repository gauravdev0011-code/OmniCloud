package com.omnicloud.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class TaskWebSocketHandler implements WebSocketHandler {

    private final TaskEventService eventService;

    public TaskWebSocketHandler(TaskEventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        // Expect URL: /ws/tasks?teamId=1
        URI uri = session.getHandshakeInfo().getUri();
        String query = uri.getQuery(); // "teamId=1"
        Long teamId = Long.parseLong(query.split("=")[1]);

        Flux<String> output = eventService.getSink(teamId).asFlux();

        return session.send(output.map(session::textMessage))
                .and(session.receive()
                        .map(msg -> msg.getPayloadAsText())
                        .doOnNext(msg -> eventService.broadcast(teamId, msg))
                        .then());
    }
}