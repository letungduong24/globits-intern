package com.example.demo.role.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    Long id;
    String role;
    String description;
}
