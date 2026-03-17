package com.omnicloud.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TaskWebSocketHandler implements WebSocketHandler {

    private final TaskEventService eventService;

    public TaskWebSocketHandler(TaskEventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        Flux<String> output = eventService.getSink().asFlux();

        return session.send(
                output.map(session::textMessage)
        ).and(
                session.receive()
                        .map(msg -> msg.getPayloadAsText())
                        .doOnNext(eventService::broadcast)
                        .then()
        );
    }
}