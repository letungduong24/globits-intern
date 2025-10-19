package com.example.demo.service;

import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user);
    UserDto deleteUserById(Long id);
    UserDto assignRole(Long userId, Long roleId);
    UserDto removeRole(Long userId, Long roleId);
}
