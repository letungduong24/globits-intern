package com.example.demo.user.service;

import com.example.demo.shared.Exception.DuplicateResourceException;
import com.example.demo.shared.Exception.ResourceNotFoundException;
import com.example.demo.user.dto.*;
import com.example.demo.user.entity.User;
import com.example.demo.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public ResponseUserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return userMapper.toDTO(user);
    }

    @Override
    public ResponseUserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return userMapper.toDTO(user);
    }

    @Override
    public ResponseUserWithPersonDto getUserWithPersonById(Long id) {
        User user = userRepository.findWithPersonById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return userMapper.toDTOWithPerson(user);
    }

    @Override
    public ResponseUserWithPersonDto getUserWithPersonByEmail(String email) {
        User user = userRepository.findWithPersonByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));
        return userMapper.toDTOWithPerson(user);
    }

    @Override
    public ResponseUserDto createUser(CreateUserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        
        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public ResponseUserDto updateUser(UpdateUserDto userDto) {
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
    public ResponseUserDto deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        ResponseUserDto dto = userMapper.toDTO(user);
        userRepository.delete(user);
        return dto;
    }

    @Override
    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
