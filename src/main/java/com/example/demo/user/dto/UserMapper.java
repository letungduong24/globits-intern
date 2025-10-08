package com.example.demo.user.dto;

import com.example.demo.person.dto.PersonMapper;
import com.example.demo.role.dto.RoleMapper;
import com.example.demo.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    private final PersonMapper personMapper;
    private final RoleMapper roleMapper;
    
    public UserMapper(PersonMapper personMapper, RoleMapper roleMapper) {
        this.personMapper = personMapper;
        this.roleMapper = roleMapper;
    }

    public ResponseUserDto toDTO(User user) {
        if (user == null) {
            return null;
        }

        return ResponseUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())
                .roles(roleMapper.toDTOs(user.getRoles()))
                .build();
    }

    public ResponseUserWithPersonDto toDTOWithPerson(User user) {
        if (user == null) {
            return null;
        }

        return ResponseUserWithPersonDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())
                .person(personMapper.toBasicDTO(user.getPerson()))
                .build();
    }
    
    public List<ResponseUserDto> toDTOs(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public User toEntity(CreateUserDto dto) {
        if (dto == null) {
            return null;
        }
        
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .isActive(dto.isActive())
                .build();
    }
    
    public User toEntity(UpdateUserDto dto) {
        if (dto == null) {
            return null;
        }
        
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
    }
}
