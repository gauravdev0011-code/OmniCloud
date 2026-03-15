package com.omnicloud.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tasks")
public class Task {

    @Id
    private Long id;
    private String content;       // Task description
    private Long assignedUserId;  // Which user this task is assigned to
    private Long teamId;          // Which team this task belongs to
    private String crdtState;     // CRDT state as JSON string

    public Task() {}

    public Task(String content, Long assignedUserId, Long teamId, String crdtState) {
        this.content = content;
        this.assignedUserId = assignedUserId;
        this.teamId = teamId;
        this.crdtState = crdtState;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(Long assignedUserId) { this.assignedUserId = assignedUserId; }
    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public String getCrdtState() { return crdtState; }
    public void setCrdtState(String crdtState) { this.crdtState = crdtState; }
}
