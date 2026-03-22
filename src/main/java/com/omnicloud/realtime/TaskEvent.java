package com.omnicloud.realtime;

import com.omnicloud.models.Task;

public class TaskEvent {

    private String type; // CREATE, UPDATE, DELETE
    private Task task;

    public TaskEvent() {}

    public TaskEvent(String type, Task task) {
        this.type = type;
        this.task = task;
    }

    public String getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}