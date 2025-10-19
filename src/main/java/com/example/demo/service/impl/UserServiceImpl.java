package com.example.demo.service.impl;

import com.example.demo.repositories.RoleRepository;
import com.example.demo.domain.Role;
import com.example.demo.service.UserService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.user.dto.*;
import com.example.demo.user.dto.request.AssignRoleDto;
import com.example.demo.user.dto.request.CreateUserDto;
import com.example.demo.user.dto.request.UpdateUserDto;
import com.example.demo.user.dto.response.UserDto;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,  RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return userMapper.toDTO(user);
    }


    @Override
    public UserDto createUser(CreateUserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }

        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDto updateUser(UpdateUserDto userDto) {
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
        
        if (userDto.getIsActive() != null) {
            user.setActive(userDto.getIsActive());
        }
        
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDto deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        UserDto dto = userMapper.toDTO(user);
        userRepository.delete(user);
        return dto;
    }

    @Override
    @Transactional
    public UserDto assignRole(AssignRoleDto assignRoleDto) {
        User user = userRepository.findById(assignRoleDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        Role role = roleRepository.findById(assignRoleDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        if (user.getRoles() != null && user.getRoles().contains(role)) {
            throw new DuplicateResourceException("User đã có role này");
        }

        user.getRoles().add(role);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDto removeRole(AssignRoleDto assignRoleDto) {
        User user = userRepository.findById(assignRoleDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        Role role = roleRepository.findById(assignRoleDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role"));

        if (user.getRoles() == null || !user.getRoles().contains(role)) {
            throw new ResourceNotFoundException("User không có role này");
        }

        user.getRoles().remove(role);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}
