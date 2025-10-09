package com.example.demo.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignRoleDto {
    @NotNull(message = "User ID không được để trống")
    Long userId;
    @NotNull(message = "Role ID không được để trống")
    Long roleId;
}
