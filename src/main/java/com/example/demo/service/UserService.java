package com.example.demo.service;

import com.example.demo.user.dto.request.AssignRoleDto;
import com.example.demo.user.dto.request.CreateUserDto;
import com.example.demo.user.dto.request.UpdateUserDto;
import com.example.demo.user.dto.response.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto createUser(CreateUserDto user);
    UserDto updateUser(UpdateUserDto user);
    UserDto deleteUserById(Long id);
    UserDto assignRole(AssignRoleDto assignRoleDto);
    UserDto removeRole(AssignRoleDto assignRoleDto);
}
