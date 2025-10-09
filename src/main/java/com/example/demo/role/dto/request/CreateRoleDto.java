package com.example.demo.role.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import lombok.experimental.FieldDefaults;

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
