package com.example.demo.user.service;

import com.example.demo.user.dto.CreateUserDto;
import com.example.demo.user.dto.ResponseUserDto;
import com.example.demo.user.dto.ResponseUserWithPersonDto;
import com.example.demo.user.dto.UpdateUserDto;

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
}
