package com.omnicloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotNull(message = "Assigned user is required")
    private Long assignedUserId;

    @NotNull(message = "Team ID is required")
    private Long teamId;

    // optional field (kept nullable by design)
    private String crdtState;
}