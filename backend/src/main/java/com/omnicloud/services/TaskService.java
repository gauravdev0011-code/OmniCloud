package com.omnicloud.services;

import com.omnicloud.exceptions.TaskNotFoundException;
import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import com.omnicloud.websocket.TaskWebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;
    private final TaskWebSocketHandler ws;

    public TaskService(TaskRepository repo, TaskWebSocketHandler ws) {
        this.repo = repo;
        this.ws = ws;
    }

    public List<Task> getAll() {
        return repo.findAll();
    }

    public Task create(Task task) {
        Task saved = repo.save(task);
        ws.broadcast("CREATE:" + saved.getId() + ":" + saved.getTitle());
        return saved;
    }

    public Task update(Long id, Task task) {
        Task existing = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + id));

        existing.setTitle(task.getTitle());
        existing.setCompleted(task.isCompleted());

        Task updated = repo.save(existing);
        ws.broadcast(
                "UPDATE:" + updated.getId() + ":" + updated.getTitle()
                        + ":" + updated.isCompleted()
        );

        return updated;
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new TaskNotFoundException("Task not found: " + id);
        }

        repo.deleteById(id);
        ws.broadcast("DELETE:" + id);
    }
}
