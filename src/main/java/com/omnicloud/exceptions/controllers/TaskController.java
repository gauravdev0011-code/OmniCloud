package com.omnicloud.exceptions.controllers;

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

    // GET all tasks
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // CREATE task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task)
                .doOnSuccess(saved ->
                        publishEvent(TaskEvent.Type.CREATE, saved));
    }

    // UPDATE task
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id,
                                 @RequestBody Task task) {

        return taskService.updateTask(id, task)
                .doOnSuccess(updated ->
                        publishEvent(TaskEvent.Type.UPDATE, updated));
    }

    // DELETE task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTask(@PathVariable Long id) {

        return taskService.deleteTask(id)
                .doOnSuccess(v ->
                        publishEvent(TaskEvent.Type.DELETE, null));
    }

    // Centralized event publishing
    private void publishEvent(TaskEvent.Type type, Task task) {
        Long userId = 1L; // replace later with authenticated user
        eventService.publish(new TaskEvent(type, task, userId));
    }
}