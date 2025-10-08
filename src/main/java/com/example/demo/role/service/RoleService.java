package com.example.demo.role.service;

import com.example.demo.role.dto.CreateRoleDto;
import com.example.demo.role.dto.ResponseRoleDto;
import com.example.demo.role.dto.UpdateRoleDto;
import com.example.demo.role.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    ResponseRoleDto createRole(CreateRoleDto createRoleDto);
    ResponseRoleDto updateRole(UpdateRoleDto updateRoleDto);
    List<ResponseRoleDto> getAllRoles();
    ResponseRoleDto getRoleById(Long id);
    ResponseRoleDto getRoleByRoleName(String role);
    ResponseRoleDto deleteRoleById(Long id);

}
