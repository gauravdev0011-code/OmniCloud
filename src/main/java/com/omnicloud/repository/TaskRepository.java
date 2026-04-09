package com.omnicloud.repository;

import com.omnicloud.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo(String assignedTo);
    List<Task> findByStatus(Task.TaskStatus status);
    List<Task> findByCreatedBy(String createdBy);
}