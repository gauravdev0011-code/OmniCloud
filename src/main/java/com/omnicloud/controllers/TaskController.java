package com.omnicloud.controllers;

import com.omnicloud.models.Task;
import com.omnicloud.services.TaskService;
import com.omnicloud.realtime.TaskEvent;
import com.omnicloud.websocket.TaskEventService;

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

    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task)
                .doOnSuccess(saved ->
                        eventService.publish(
                                new TaskEvent("CREATE", saved, 1L)
                        ));
    }

    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id,
                                 @RequestBody Task task) {

        return taskService.updateTask(id, task)
                .doOnSuccess(updated ->
                        eventService.publish(
                                new TaskEvent("UPDATE", updated, 1L)
                        ));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {

        return taskService.deleteTask(id)
                .doOnSuccess(v ->
                        eventService.publish(
                                new TaskEvent("DELETE", null, 1L)
                        ));
    }
}