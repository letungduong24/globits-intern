package com.example.demo.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UpdateTaskDto {
    @NotBlank(message = "ID không được để trống")
    Long id;
    String name;
    LocalDate startTime;
    LocalDate endTime;
    String description;
    Integer priority;
    Integer status;
}
