package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks
    public Flux<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    // Get task by ID
    public Mono<Task> getTaskById(Long id) {
        logger.info("Fetching task with id: {}", id);
        return taskRepository.findById(id);
    }

    // Create task
    public Mono<Task> createTask(Task task) {
        logger.info("Creating new task for team {}", task.getTeamId());
        return taskRepository.save(task);
    }

    // Update task
    public Mono<Task> updateTask(Long id, Task updatedTask) {

        logger.info("Updating task {}", id);

        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setContent(updatedTask.getContent());
                    existingTask.setAssignedUserId(updatedTask.getAssignedUserId());
                    existingTask.setTeamId(updatedTask.getTeamId());
                    existingTask.setCrdtState(updatedTask.getCrdtState());
                    return taskRepository.save(existingTask);
                });
    }

    // Delete task
    public Mono<Void> deleteTask(Long id) {
        logger.info("Deleting task {}", id);
        return taskRepository.deleteById(id);
    }
}