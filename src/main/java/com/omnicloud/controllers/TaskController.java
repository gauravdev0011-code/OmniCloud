package com.omnicloud.controllers;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Create new task
    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by id
    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id);
    }
}