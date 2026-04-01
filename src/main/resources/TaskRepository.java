package com.omnicloud.repository;

import com.omnicloud.models.Task;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository
        extends ReactiveCrudRepository<Task, Long> {
}