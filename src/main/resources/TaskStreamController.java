package com.omnicloud.controllers;

import com.omnicloud.realtime.TaskEvent;
import com.omnicloud.realtime.TaskEventPublisher;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TaskStreamController {

    private final TaskEventPublisher publisher;

    public TaskStreamController(TaskEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping(value = "/tasks/stream",
            produces = "text/event-stream")
    public Flux<TaskEvent> streamTasks() {
        return publisher.getTaskStream();
    }
}