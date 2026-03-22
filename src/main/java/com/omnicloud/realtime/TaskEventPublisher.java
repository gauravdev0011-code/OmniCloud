package com.omnicloud.realtime;

import com.omnicloud.models.Task;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class TaskEventPublisher {

    // Multicast sink for real-time events
    private final Sinks.Many<Task> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void publish(Task task) {
        sink.tryEmitNext(task);
    }

    public Flux<Task> getTaskStream() {
        return sink.asFlux();
    }
}