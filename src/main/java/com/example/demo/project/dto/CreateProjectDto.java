package com.example.demo.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProjectDto {
    @NotBlank(message = "Mã dự án không được để trống")
    String code;
    @NotBlank(message = "Tên dự án không được để trống")
    String name;
    String description;
    Long companyId;
}

