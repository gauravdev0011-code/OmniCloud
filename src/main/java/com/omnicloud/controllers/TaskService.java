package com.omnicloud.services;

import com.omnicloud.models.Task;
import com.omnicloud.repository.TaskRepository;
import com.omnicloud.realtime.TaskEvent;
import com.omnicloud.realtime.TaskEventPublisher;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskEventPublisher publisher;

    public TaskService(TaskRepository taskRepository,
                       TaskEventPublisher publisher) {
        this.taskRepository = taskRepository;
        this.publisher = publisher;
    }

    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Mono<Task> createTask(Task task) {
        return taskRepository.save(task)
                .doOnSuccess(saved ->
                        publisher.publish(
                                new TaskEvent("CREATE", saved)
                        ));
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
                .doOnSuccess(saved ->
                        publisher.publish(
                                new TaskEvent("UPDATE", saved)
                        ));
    }

    public Mono<Void> deleteTask(Long id) {

        return taskRepository.findById(id)
                .flatMap(task ->
                        taskRepository.deleteById(id)
                                .doOnSuccess(v ->
                                        publisher.publish(
                                                new TaskEvent("DELETE", task)
                                        )
                                )
                );
    }
}