package com.example.demo.user.dto.response;

import com.example.demo.person.dto.response.PersonDto;
import com.example.demo.role.dto.response.RoleDto;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    Long id;
    String email;
    boolean isActive;
    Set<RoleDto> roles;
    PersonDto person;
}
