package com.omnicloud.controllers;

import com.omnicloud.models.Task;
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

    // Server-Sent Events stream
    @GetMapping(value = "/tasks/stream",
            produces = "text/event-stream")
    public Flux<Task> streamTasks() {
        return publisher.getTaskStream();
    }
}