package com.example.demo.service;

import com.example.demo.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(RoleDto roleDto);
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleDto getRoleByRoleName(String role);
    RoleDto deleteRoleById(Long id);
}
