package com.omnicloud.realtime;

import com.omnicloud.models.Task;

public class TaskEvent {

    private String type; // CREATE, UPDATE, DELETE
    private Task task;
    private Long performedByUserId;
    private long timestamp;

    public TaskEvent() {}

    public TaskEvent(String type, Task task, Long performedByUserId) {
        this.type = type;
        this.task = task;
        this.performedByUserId = performedByUserId;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getType() { return type; }
    public Task getTask() { return task; }
    public Long getPerformedByUserId() { return performedByUserId; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setType(String type) { this.type = type; }
    public void setTask(Task task) { this.task = task; }
    public void setPerformedByUserId(Long performedByUserId) { this.performedByUserId = performedByUserId; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}