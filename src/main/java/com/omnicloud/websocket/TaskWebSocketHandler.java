package com.omnicloud.websocket;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.WebSocketMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class TaskWebSocketHandler implements WebSocketHandler {

    // Shared stream for all connected clients
    private final Sinks.Many<String> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    // called by service when event happens
    public void broadcast(String message) {
        sink.tryEmitNext(message);
    }

    @Override
    public Flux<Void> handle(WebSocketSession session) {

        Flux<WebSocketMessage> output =
                sink.asFlux()
                        .map(session::textMessage);

        return session.send(output);
    }
}