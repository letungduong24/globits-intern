package com.example.demo.service;

import com.example.demo.role.dto.request.CreateRoleDto;
import com.example.demo.role.dto.response.RoleDto;
import com.example.demo.role.dto.request.UpdateRoleDto;

import java.util.List;

public interface RoleService {
    RoleDto createRole(CreateRoleDto createRoleDto);
    RoleDto updateRole(UpdateRoleDto updateRoleDto);
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleDto getRoleByRoleName(String role);
    RoleDto deleteRoleById(Long id);

}
