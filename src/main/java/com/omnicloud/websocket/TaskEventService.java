package com.omnicloud.websocket;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class TaskEventService {

    private final Sinks.Many<String> sink =
            Sinks.many().multicast().directBestEffort();

    public void broadcast(String message) {
        sink.tryEmitNext(message);
    }

    public Sinks.Many<String> getSink() {
        return sink;
    }
}