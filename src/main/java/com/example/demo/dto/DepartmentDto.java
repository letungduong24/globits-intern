package com.example.demo.dto;

import com.example.demo.domain.Department;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentDto {
    Long id;
    
    @NotBlank(message = "Tên phòng ban không được để trống")
    String name;
    
    @NotBlank(message = "Mã phòng ban không được để trống")
    String code;
    
    Long parentId;
    String parentName;
    
    Long companyId;
    String companyName;

    /**
     * Constructor cho HQL queries
     */
    public DepartmentDto(Long id, String name, String code, Long parentId, String parentName, Long companyId, String companyName, boolean forHql) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.parentId = parentId;
        this.parentName = parentName;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Department toEntity() {
        return Department.builder()
                .id(this.id)
                .name(this.name)
                .code(this.code)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static DepartmentDto fromEntity(Department department) {
        if (department == null) {
            return null;
        }
        
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .parentId(department.getParent() != null ? department.getParent().getId() : null)
                .parentName(department.getParent() != null ? department.getParent().getName() : null)
                .companyId(department.getCompany() != null ? department.getCompany().getId() : null)
                .companyName(department.getCompany() != null ? department.getCompany().getName() : null)
                .build();
    }
}
