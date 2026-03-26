package com.omnicloud.controllers;

import com.omnicloud.models.Task;
import com.omnicloud.services.TaskService;
import com.omnicloud.realtime.TaskEvent;
import com.omnicloud.websocket.TaskEventService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskEventService eventService;

    public TaskController(TaskService taskService,
                          TaskEventService eventService) {
        this.taskService = taskService;
        this.eventService = eventService;
    }

    // Fetch all tasks
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks()
                .switchIfEmpty(Flux.empty()); // ensures empty list instead of null
    }

    // Create a new task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task)
                .doOnSuccess(saved -> publishEvent("CREATE", saved));
    }

    // Update a task
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id,
                                 @RequestBody Task task) {
        return taskService.updateTask(id, task)
                .flatMap(updated -> {
                    if (updated != null) {
                        publishEvent("UPDATE", updated);
                        return Mono.just(updated);
                    }
                    return Mono.empty();
                });
    }

    // Delete a task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id)
                .doOnSuccess(v -> publishEvent("DELETE", null));
    }

    // Centralized event publishing
    private void publishEvent(String action, Task task) {
        // In real app, fetch userId dynamically from auth context
        Long userId = 1L;
        eventService.publish(new TaskEvent(action, task, userId));
    }
}