package com.omnicloud.realtime;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Component
public class TaskEventPublisher {

    private final Sinks.Many<TaskEvent> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    /**
     * Publishes a task event to all subscribers.
     * @param event The TaskEvent to publish
     */
    public void publish(TaskEvent event) {
        EmitResult result = sink.tryEmitNext(event);
        if (result.isFailure()) {
            // You can log the failure here or handle it
            System.err.println("Failed to emit task event: " + result);
        }
    }

    /**
     * Returns a Flux that subscribers can listen to for TaskEvents.
     * @return Flux<TaskEvent>
     */
    public Flux<TaskEvent> getTaskStream() {
        return sink.asFlux();
    }
}