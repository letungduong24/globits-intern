package com.example.demo.role.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseRoleDto {
    Long id;
    String role;
    String description;
}
