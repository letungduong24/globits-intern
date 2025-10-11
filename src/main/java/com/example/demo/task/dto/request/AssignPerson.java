package com.example.demo.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignPerson {
    @NotNull(message = "Person ID không được để trống")
    Long personId;
    
    @NotNull(message = "Task ID không được để trống")
    Long taskId;
}
