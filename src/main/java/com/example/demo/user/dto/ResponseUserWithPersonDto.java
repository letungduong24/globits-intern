package com.example.demo.user.dto;

import com.example.demo.person.dto.PersonBasicDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserWithPersonDto {
    Long id;
    String email;
    boolean isActive;
    PersonBasicDto person;
}
