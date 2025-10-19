package com.example.demo.dto;

import com.example.demo.domain.Project;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {
    Long id;
    
    @NotBlank(message = "Tên dự án không được để trống")
    String name;
    
    @NotBlank(message = "Mã dự án không được để trống")
    String code;
    
    String description;
    
    Long companyId;
    String companyName;

    /**
     * Constructor cho HQL queries
     */
    public ProjectDto(Long id, String name, String code, String description, Long companyId, String companyName, boolean forHql) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Project toEntity() {
        return Project.builder()
                .id(this.id)
                .name(this.name)
                .code(this.code)
                .description(this.description)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static ProjectDto fromEntity(Project project) {
        if (project == null) {
            return null;
        }
        
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .description(project.getDescription())
                .companyId(project.getCompany() != null ? project.getCompany().getId() : null)
                .companyName(project.getCompany() != null ? project.getCompany().getName() : null)
                .build();
    }
}
