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

    // Create task
    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id);
    }

    // Update task
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {

        return taskRepository.findById(id)
                .flatMap(task -> {
                    task.setContent(updatedTask.getContent());
                    task.setAssignedUserId(updatedTask.getAssignedUserId());
                    task.setTeamId(updatedTask.getTeamId());
                    task.setCrdtState(updatedTask.getCrdtState());
                    return taskRepository.save(task);
                });
    }

    // Delete task
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return taskRepository.deleteById(id);
    }
}