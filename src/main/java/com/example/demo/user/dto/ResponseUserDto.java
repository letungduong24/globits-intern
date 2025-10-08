package com.example.demo.user.dto;

import com.example.demo.role.dto.ResponseRoleDto;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserDto {
    Long id;
    String email;
    boolean isActive;
    Set<ResponseRoleDto> roles;
}
