package com.example.demo.task.dto.response;

import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.project.dto.response.BasicProjectDto;
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
public class TaskDto {
    Long id;
    String name;
    LocalDate startTime;
    LocalDate endTime;
    String description;
    Integer priority;
    Integer status;
    PersonDto person;
    BasicProjectDto project;
}
