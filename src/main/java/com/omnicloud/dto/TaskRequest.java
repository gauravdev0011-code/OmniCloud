package com.omnicloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequest {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotNull(message = "Assigned user is required")
    private Long assignedUserId;

    @NotNull(message = "Team ID is required")
    private Long teamId;

    // optional field (kept nullable by design)
    private String crdtState;

    public TaskRequest() {}

    public TaskRequest(String content, Long assignedUserId, Long teamId, String crdtState) {
        this.content = content;
        this.assignedUserId = assignedUserId;
        this.teamId = teamId;
        this.crdtState = crdtState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getCrdtState() {
        return crdtState;
    }

    public void setCrdtState(String crdtState) {
        this.crdtState = crdtState;
    }
}