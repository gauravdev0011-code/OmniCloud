package com.omnicloud.controllers;

import com.omnicloud.models.Task;
import com.omnicloud.services.TaskService;
import com.omnicloud.dto.TaskRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // Create task using DTO validation
    @PostMapping
    public Mono<Task> createTask(@Valid @RequestBody TaskRequest request) {

        Task task = new Task(
                request.getContent(),
                request.getAssignedUserId(),
                request.getTeamId(),
                request.getCrdtState()
        );

        return taskService.createTask(task);
    }

    // Update task
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id,
                                 @Valid @RequestBody TaskRequest request) {

        Task task = new Task(
                request.getContent(),
                request.getAssignedUserId(),
                request.getTeamId(),
                request.getCrdtState()
        );

        return taskService.updateTask(id, task);
    }

    // Delete task
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}