package com.example.demo.task.dto.request;

import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.project.dto.response.BasicProjectDto;
import jakarta.validation.constraints.NotNull;
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

public class CreateTaskDto {
    String name;
    LocalDate startTime;
    LocalDate endTime;
    String description;
    Integer priority;
    Integer status;
    @NotNull(message = "Project Id không được để trống")
    Long projectId;
    @NotNull(message = "Person Id không được để trống")
    Long personId;
}
