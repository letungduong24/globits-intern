package com.example.demo.task.dto.request;

import jakarta.validation.constraints.NotNull;

public class AssignProject {
    @NotNull(message = "Project ID không được để trống")
    Long projectId;
    @NotNull(message = "Task ID không được để trống")
    Long taskId;
}
