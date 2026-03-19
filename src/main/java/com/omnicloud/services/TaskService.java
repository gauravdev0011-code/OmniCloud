package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import com.omnicloud.websocket.TaskEventService;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskEventService eventService;

    public TaskService(TaskRepository taskRepository,
                       TaskEventService eventService) {
        this.taskRepository = taskRepository;
        this.eventService = eventService;
    }

    // GET ALL
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // GET BY ID
    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // CREATE
    public Mono<Task> createTask(Task task) {
        return taskRepository.save(task)
                .doOnSuccess(saved ->
                        eventService.broadcast(
                                saved.getTeamId(),
                                "TASK_CREATED:" + saved.getId()));
    }

    // UPDATE
    public Mono<Task> updateTask(Long id, Task updatedTask) {

        return taskRepository.findById(id)
                .flatMap(task -> {
                    task.setContent(updatedTask.getContent());
                    task.setAssignedUserId(updatedTask.getAssignedUserId());
                    task.setTeamId(updatedTask.getTeamId());
                    task.setCrdtState(updatedTask.getCrdtState());

                    return taskRepository.save(task)
                            .doOnSuccess(t ->
                                    eventService.broadcast(
                                            t.getTeamId(),
                                            "TASK_UPDATED:" + t.getId()));
                });
    }

    // DELETE
    public Mono<Void> deleteTask(Long id) {

        return taskRepository.findById(id)
                .flatMap(task ->
                        taskRepository.delete(task)
                                .doOnSuccess(v ->
                                        eventService.broadcast(
                                                task.getTeamId(),
                                                "TASK_DELETED:" + id)));
    }
}