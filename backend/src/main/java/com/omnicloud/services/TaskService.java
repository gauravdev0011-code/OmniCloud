package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> getAll() {
        return repo.findAll();
    }

    public Task create(Task task) {
        return repo.save(task);
    }

    public Task update(Long id, Task task) {
        Task existing = repo.findById(id).orElseThrow();
        existing.setTitle(task.getTitle());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}