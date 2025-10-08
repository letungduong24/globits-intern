package com.example.demo.project.dto;

import com.example.demo.project.entity.Project;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public ResponseProjectDto toDTO(Project project) {
        if (project == null) {
            return null;
        }
        
        return ResponseProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .description(project.getDescription())
                .companyId(project.getCompany() != null ? project.getCompany().getId() : null)
                .build();
    }

    public List<ResponseProjectDto> toDTOs(List<Project> projects) {
        if (projects == null) {
            return null;
        }
        
        return projects.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BasicProjectDto toBasicDTO(Project project) {
        if (project == null) {
            return null;
        }
        
        return BasicProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .build();
    }

    public List<BasicProjectDto> toBasicDTOs(List<Project> projects) {
        if (projects == null) {
            return null;
        }
        
        return projects.stream()
                .map(this::toBasicDTO)
                .collect(Collectors.toList());
    }

    public Project toEntity(CreateProjectDto projectDto) {
        if (projectDto == null) {
            return null;
        }
        
        return Project.builder()
                .name(projectDto.getName())
                .code(projectDto.getCode())
                .description(projectDto.getDescription())
                .build();
    }

    public Project updateEntity(Project existingProject, UpdateProjectDto updateDto) {
        if (updateDto.getName() != null) {
            existingProject.setName(updateDto.getName());
        }
        if (updateDto.getCode() != null) {
            existingProject.setCode(updateDto.getCode());
        }
        if (updateDto.getDescription() != null) {
            existingProject.setDescription(updateDto.getDescription());
        }
        
        return existingProject;
    }
}

