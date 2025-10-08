package com.example.demo.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProjectDto {
    @NotNull(message = "ID không được để trống")
    Long id;
    String code;
    String name;
    String description;
}

