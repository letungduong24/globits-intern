package com.example.demo.role.service;

import com.example.demo.role.RoleRepository;
import com.example.demo.role.dto.CreateRoleDto;
import com.example.demo.role.dto.ResponseRoleDto;
import com.example.demo.role.dto.RoleMapper;
import com.example.demo.role.dto.UpdateRoleDto;
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
    public ResponseRoleDto createRole(CreateRoleDto createRoleDto) {
        roleRepository.findByRoleIgnoreCase(createRoleDto.getRole()).ifPresent(role -> {
            throw new DuplicateResourceException("Role đã tồn tại");
        });

        Role role = roleMapper.toEntity(createRoleDto);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Override
    public ResponseRoleDto updateRole(UpdateRoleDto roleDto) {
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
    public List<ResponseRoleDto> getAllRoles() {

        return roleMapper.toDTOs(roleRepository.findAll());
    }

    @Override
    public ResponseRoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
        return roleMapper.toDTO(role);
    }

    @Override
    public ResponseRoleDto getRoleByRoleName(String name) {
        Role role = roleRepository.findByRoleIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
        return roleMapper.toDTO(role);
    }

    @Override
    public ResponseRoleDto deleteRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        ResponseRoleDto dto = roleMapper.toDTO(role);
        roleRepository.delete(role);
        return dto;
    }
}
