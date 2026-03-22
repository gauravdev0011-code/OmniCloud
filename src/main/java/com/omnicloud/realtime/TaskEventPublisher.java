package com.omnicloud.realtime;

import com.omnicloud.models.Task;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class TaskEventPublisher {

    private final Sinks.Many<TaskEvent> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    // publish CREATE / UPDATE / DELETE
    public void publish(TaskEvent event) {
        sink.tryEmitNext(event);
    }

    public Flux<TaskEvent> getTaskStream() {
        return sink.asFlux();
    }
}