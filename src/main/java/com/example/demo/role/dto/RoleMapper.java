package com.example.demo.role.dto;

import com.example.demo.role.dto.request.CreateRoleDto;
import com.example.demo.role.dto.request.UpdateRoleDto;
import com.example.demo.role.dto.response.RoleDto;
import com.example.demo.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleDto toDTO(Role role) {
        if (role == null) {
            return null;
        }
        
        return RoleDto.builder()
                .id(role.getId())
                .role(role.getRole())
                .description(role.getDescription())
                .build();
    }

    public List<RoleDto> toDTOs(List<Role> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Set<RoleDto> toDTOs(Set<Role> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Role toEntity(CreateRoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        
        return Role.builder()
                .role(roleDto.getRole())
                .description(roleDto.getDescription())
                .build();
    }

    public Role updateEntity(Role existingRole, UpdateRoleDto updateDto) {
        if (updateDto.getRole() != null) {
            existingRole.setRole(updateDto.getRole());
        }
        if (updateDto.getDescription() != null) {
            existingRole.setDescription(updateDto.getDescription());
        }
        return existingRole;
    }
}
