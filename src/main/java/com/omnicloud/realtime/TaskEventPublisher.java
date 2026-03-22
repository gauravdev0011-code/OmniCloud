package com.omnicloud.realtime;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class TaskEventPublisher {

    private final Sinks.Many<TaskEvent> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void publish(TaskEvent event) {
        sink.tryEmitNext(event);
    }

    public Flux<TaskEvent> getTaskStream() {
        return sink.asFlux();
    }
}