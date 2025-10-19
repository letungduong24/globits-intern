package com.example.demo.service.impl;

import com.example.demo.repositories.RoleRepository;
import com.example.demo.domain.Role;
import com.example.demo.service.UserService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.dto.UserDto;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAllWithRoles();
        return users.stream()
                .map(UserDto::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return UserDto.fromEntity(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return UserDto.fromEntity(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }

        User user = userDto.toEntity();
        User saved = userRepository.save(user);
        return UserDto.fromEntity(saved);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new DuplicateResourceException("Email đã tồn tại");
            }
            user.setEmail(userDto.getEmail());
        }
        
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        
        user.setActive(userDto.isActive());
        
        User saved = userRepository.save(user);
        return UserDto.fromEntity(saved);
    }

    @Override
    public UserDto deleteUserById(Long id) {
        UserDto dto = userRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        
        userRepository.deleteById(id);
        return dto;
    }

    @Override
    @Transactional
    public UserDto assignRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        if (user.getRoles() == null) {
            user.setRoles(new java.util.HashSet<>());
        }
        
        if (user.getRoles().contains(role)) {
            throw new DuplicateResourceException("User đã có role này");
        }

        user.getRoles().add(role);

        User savedUser = userRepository.save(user);
        return UserDto.fromEntity(savedUser);
    }

    @Override
    @Transactional
    public UserDto removeRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        if (user.getRoles() == null || !user.getRoles().contains(role)) {
            throw new ResourceNotFoundException("User không có role này");
        }

        user.getRoles().remove(role);

        User savedUser = userRepository.save(user);
        return UserDto.fromEntity(savedUser);
    }
}
