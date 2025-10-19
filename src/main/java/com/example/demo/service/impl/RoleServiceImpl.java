package com.example.demo.service.impl;

import com.example.demo.repositories.RoleRepository;
import com.example.demo.dto.RoleDto;
import com.example.demo.domain.Role;
import com.example.demo.service.RoleService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        roleRepository.findByRoleIgnoreCase(roleDto.getRole()).ifPresent(role -> {
            throw new DuplicateResourceException("Role đã tồn tại");
        });

        Role role = roleDto.toEntity();
        Role saved = roleRepository.save(role);
        return RoleDto.fromEntity(saved);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        Role role = roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        if (
                roleDto.getRole() != null &&
                        !roleDto.getRole().equals(role.getRole()) &&
                        roleRepository.existsByRoleAndIdNot(roleDto.getRole(), roleDto.getId())
        ) {
            throw new DuplicateResourceException("Role đã tồn tại");
        }

        // Cập nhật các trường
        if (roleDto.getRole() != null) {
            role.setRole(roleDto.getRole());
        }
        if (roleDto.getDescription() != null) {
            role.setDescription(roleDto.getDescription());
        }
        
        Role saved = roleRepository.save(role);
        return RoleDto.fromEntity(saved);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAllAsDto();
    }

    @Override
    public RoleDto getRoleById(Long id) {
        return roleRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
    }

    @Override
    public RoleDto getRoleByRoleName(String name) {
        return roleRepository.findByRoleIgnoreCaseAsDto(name)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
    }

    @Override
    public RoleDto deleteRoleById(Long id) {
        RoleDto dto = roleRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));
        
        roleRepository.deleteById(id);
        return dto;
    }
}
