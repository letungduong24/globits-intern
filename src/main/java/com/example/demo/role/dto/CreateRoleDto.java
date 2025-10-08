package com.example.demo.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRoleDto {
    @NotBlank(message = "Role không được để trống")
    String role;
    String description;
}
