package com.example.demo.role.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRoleDto {
    @NotNull(message = "ID không được để trống")
    Long id;
    @Min(value = 2,message = "Role không ít hơn 2 ký tự")
    String role;
    String description;
}
