package com.example.demo.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignProject {
    @NotNull(message = "Project ID không được để trống")
    Long projectId;
    
    @NotNull(message = "Task ID không được để trống")
    Long taskId;
}
