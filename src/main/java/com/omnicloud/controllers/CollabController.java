package com.omnicloud.controllers;

import com.omnicloud.utils.CRDT;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

@RestController
public class CollabController {

    // Keep all connected sessions
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public WebSocketHandler collabHandler() {
        return session -> {
            sessions.add(session);

            // Receive messages from client
            Mono<Void> input = session.receive()
                    .map(msg -> msg.getPayloadAsText())
                    .doOnNext(this::broadcast)
                    .then();

            // Keep connection open
            Mono<Void> output = session.send(Flux.never());

            return Mono.zip(input, output).then()
                    .doFinally(sig -> sessions.remove(session));
        };
    }

    // Broadcast to all other sessions
    private void broadcast(String msg) {
        String merged = CRDT.mergeEdits(List.of(msg)); // simple merge
        sessions.forEach(s -> s.send(Mono.just(s.textMessage(merged))).subscribe());
    }
}