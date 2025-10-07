package com.example.demo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto {
    Long id;
    
    @Email(message = "Email không hợp lệ")
    String email;
    
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    String password;
    
    Boolean isActive;
}
