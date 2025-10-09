package com.example.demo.role.service;

import com.example.demo.role.RoleRepository;
import com.example.demo.role.dto.request.CreateRoleDto;
import com.example.demo.role.dto.response.RoleDto;
import com.example.demo.role.dto.RoleMapper;
import com.example.demo.role.dto.request.UpdateRoleDto;
import com.example.demo.role.entity.Role;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto createRole(CreateRoleDto createRoleDto) {
        roleRepository.findByRoleIgnoreCase(createRoleDto.getRole()).ifPresent(role -> {
            throw new DuplicateResourceException("Role đã tồn tại");
        });

        Role role = roleMapper.toEntity(createRoleDto);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Override
    public RoleDto updateRole(UpdateRoleDto roleDto) {
        Role role = roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        if (
                roleDto.getRole() != null &&
                        !roleDto.getRole().equals(role.getRole()) &&
                        roleRepository.existsByRoleAndIdNot(roleDto.getRole(), roleDto.getId())
        ) {
            throw new DuplicateResourceException("Role đã tồn tại");
        }

        roleMapper.updateEntity(role, roleDto);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {

        return roleMapper.toDTOs(roleRepository.findAll());
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
        return roleMapper.toDTO(role);
    }

    @Override
    public RoleDto getRoleByRoleName(String name) {
        Role role = roleRepository.findByRoleIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
        return roleMapper.toDTO(role);
    }

    @Override
    public RoleDto deleteRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        RoleDto dto = roleMapper.toDTO(role);
        roleRepository.delete(role);
        return dto;
    }
}
