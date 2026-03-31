package com.omnicloud.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tasks")
public class Task {

    @Id
    private Long id;

    private String content;
    private Long assignedUserId;
    private Long teamId;
    private String crdtState;

    public Task() {}

    public Task(String content, Long assignedUserId,
                Long teamId, String crdtState) {
        this.content = content;
        this.assignedUserId = assignedUserId;
        this.teamId = teamId;
        this.crdtState = crdtState;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    public String getCrdtState() { return crdtState; }
    public void setCrdtState(String crdtState) {
        this.crdtState = crdtState;
    }
}