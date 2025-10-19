package com.example.demo.dto;

import com.example.demo.domain.User;
import com.example.demo.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    String email;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    String password;
    
    boolean isActive;
    
    Set<RoleDto> roles;

    /**
     * Constructor cho HQL queries
     */
    public UserDto(Long id, String email, String password, boolean isActive, boolean forHql) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public User toEntity() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .isActive(this.isActive)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static UserDto fromEntity(User user) {
        if (user == null) {
            return null;
        }
        
        Set<RoleDto> roles = null;
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            roles = user.getRoles().stream()
                    .map(RoleDto::fromEntity)
                    .collect(java.util.stream.Collectors.toSet());
        }
        
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActive(user.isActive())
                .roles(roles)
                .build();
    }
}
