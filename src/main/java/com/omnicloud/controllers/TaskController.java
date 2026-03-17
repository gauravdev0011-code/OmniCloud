package com.omnicloud.controllers;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import com.omnicloud.websocket.TaskEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskEventService eventService;

    // -----------------------------
    // GET ALL TASKS
    // -----------------------------
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // -----------------------------
    // GET TASK BY ID
    // -----------------------------
    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id);
    }

    // -----------------------------
    // CREATE TASK
    // -----------------------------
    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {

        return taskRepository.save(task)
                .doOnSuccess(savedTask ->
                        eventService.broadcast(
                                "TASK_CREATED:" + savedTask.getId()
                        ));
    }

    // -----------------------------
    // UPDATE TASK
    // -----------------------------
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id,
                                 @RequestBody Task updatedTask) {

        return taskRepository.findById(id)
                .flatMap(task -> {
                    task.setContent(updatedTask.getContent());
                    task.setAssignedUserId(updatedTask.getAssignedUserId());
                    task.setTeamId(updatedTask.getTeamId());
                    task.setCrdtState(updatedTask.getCrdtState());

                    return taskRepository.save(task)
                            .doOnSuccess(t ->
                                    eventService.broadcast(
                                            "TASK_UPDATED:" + t.getId()
                                    ));
                });
    }

    // -----------------------------
    // DELETE TASK
    // -----------------------------
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {

        return taskRepository.deleteById(id)
                .doOnSuccess(v ->
                        eventService.broadcast(
                                "TASK_DELETED:" + id
                        ));
    }
}