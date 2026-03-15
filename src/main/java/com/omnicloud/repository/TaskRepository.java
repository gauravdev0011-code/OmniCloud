package com.omnicloud.repository;

import com.omnicloud.models.Task;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    Mono<Task> findById(Long id);
}
