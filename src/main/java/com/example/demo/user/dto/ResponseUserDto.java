package com.example.demo.user.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserDto {
    Long id;
    String email;
    boolean isActive;
}
