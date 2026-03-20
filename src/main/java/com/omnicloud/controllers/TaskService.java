package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // ======================
    // GET ALL TASKS
    // ======================
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ======================
    // GET TASK BY ID
    // ======================
    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // ======================
    // CREATE TASK
    // ======================
    public Mono<Task> createTask(Task task) {
        return taskRepository.save(task);
    }

    // ======================
    // UPDATE TASK
    // ======================
    public Mono<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .flatMap(task -> {
                    task.setContent(updatedTask.getContent());
                    task.setAssignedUserId(updatedTask.getAssignedUserId());
                    task.setTeamId(updatedTask.getTeamId());
                    task.setCrdtState(updatedTask.getCrdtState());
                    return taskRepository.save(task);
                });
    }

    // ======================
    // DELETE TASK
    // ======================
    public Mono<Void> deleteTask(Long id) {
        return taskRepository.deleteById(id);
    }
}