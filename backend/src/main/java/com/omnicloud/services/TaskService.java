package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import com.omnicloud.websocket.TaskWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;
    private final TaskWebSocketHandler socketHandler;

    public TaskService(TaskRepository repository,
                       TaskWebSocketHandler socketHandler) {
        this.repository = repository;
        this.socketHandler = socketHandler;
    }

    // GET ALL TASKS
    public List<Task> getAll() {
        logger.info("Fetching all tasks");
        return repository.findAll();
    }

    // CREATE TASK (WITH REAL-TIME BROADCAST)
    public Task create(Task task) {
        logger.info("Creating task: {}", task.getTitle());

        Task saved = repository.save(task);

        String message = String.format(
                "{\"type\":\"CREATE\",\"id\":%d,\"title\":\"%s\",\"description\":\"%s\"}",
                saved.getId(),
                saved.getTitle(),
                saved.getDescription()
        );

        socketHandler.broadcast(message);

        return saved;
    }

    // UPDATE TASK
    public Task update(Long id, Task updatedTask) {
        logger.info("Updating task with id: {}", id);

        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());

        return repository.save(task);
    }

    // DELETE TASK
    public void delete(Long id) {
        logger.info("Deleting task with id: {}", id);
        repository.deleteById(id);
    }
}