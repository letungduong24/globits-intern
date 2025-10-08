package com.example.demo.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignPersonDto {
    @NotNull(message = "Project ID không được để trống")
    Long projectId;
    @NotNull(message = "Person ID không được để trống")
    Long personId;
}

