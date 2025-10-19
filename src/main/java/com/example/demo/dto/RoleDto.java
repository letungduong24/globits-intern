package com.example.demo.dto;

import com.example.demo.domain.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {
    Long id;
    
    @NotBlank(message = "Tên vai trò không được để trống")
    String role;
    
    String description;

    /**
     * Constructor cho HQL queries
     */
    public RoleDto(Long id, String role, String description, boolean forHql) {
        this.id = id;
        this.role = role;
        this.description = description;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Role toEntity() {
        return Role.builder()
                .id(this.id)
                .role(this.role)
                .description(this.description)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static RoleDto fromEntity(Role role) {
        if (role == null) {
            return null;
        }
        
        return RoleDto.builder()
                .id(role.getId())
                .role(role.getRole())
                .description(role.getDescription())
                .build();
    }
}
