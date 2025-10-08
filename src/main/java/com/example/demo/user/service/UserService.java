package com.example.demo.user.service;

import com.example.demo.user.dto.*;

import java.util.List;

public interface UserService {
    List<ResponseUserDto> getAllUsers();
    ResponseUserDto getUserById(Long id);
    ResponseUserWithPersonDto getUserWithPersonById(Long id);
    ResponseUserWithPersonDto getUserWithPersonByEmail(String email);
    ResponseUserDto getUserByEmail(String email);
    ResponseUserDto createUser(CreateUserDto user);
    ResponseUserDto updateUser(UpdateUserDto user);
    ResponseUserDto deleteUserById(Long id);
    boolean existsUserById(Long id);
    boolean existsUserByEmail(String email);
    ResponseUserDto assignRole(AssignRoleDto assignRoleDto);
    ResponseUserDto removeRole(AssignRoleDto assignRoleDto);
}
