package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import com.omnicloud.realtime.TaskEventPublisher;

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
    private final TaskEventPublisher publisher;

    public TaskService(TaskRepository taskRepository,
                       TaskEventPublisher publisher) {
        this.taskRepository = taskRepository;
        this.publisher = publisher;
    }

    public Flux<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    public Mono<Task> getTaskById(Long id) {
        logger.info("Fetching task {}", id);
        return taskRepository.findById(id);
    }

    public Mono<Task> createTask(Task task) {
        logger.info("Creating task");

        return taskRepository.save(task)
                .doOnSuccess(publisher::publish);
    }

    public Mono<Task> updateTask(Long id, Task updatedTask) {

        return taskRepository.findById(id)
                .flatMap(existing -> {
                    existing.setContent(updatedTask.getContent());
                    existing.setAssignedUserId(updatedTask.getAssignedUserId());
                    existing.setTeamId(updatedTask.getTeamId());
                    existing.setCrdtState(updatedTask.getCrdtState());
                    return taskRepository.save(existing);
                })
                .doOnSuccess(publisher::publish);
    }

    public Mono<Void> deleteTask(Long id) {
        logger.info("Deleting task {}", id);
        return taskRepository.deleteById(id);
    }
}