package com.example.demo.project.dto;

import com.example.demo.company.dto.CompanyMapper;
import com.example.demo.person.dto.PersonMapper;
import com.example.demo.project.dto.request.CreateProjectDto;
import com.example.demo.project.dto.response.BasicProjectDto;
import com.example.demo.project.dto.request.UpdateProjectDto;
import com.example.demo.project.dto.response.ProjectDto;
import com.example.demo.project.entity.Project;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private CompanyMapper companyMapper;
    private PersonMapper personMapper;

    public ProjectMapper(
            CompanyMapper companyMapper,
            PersonMapper personMapper
    ){
        this.companyMapper = companyMapper;
        this.personMapper = personMapper;
    }

    public ProjectDto toDTO(Project project) {
        if (project == null) {
            return null;
        }
        
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .description(project.getDescription())
                .company(project.getCompany() != null ? companyMapper.toDTO(project.getCompany()) : null)
                .persons(project.getPersons() != null ? personMapper.toDTOs(project.getPersons()) : null)
                .build();
    }

    public List<ProjectDto> toDTOs(List<Project> projects) {
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
                .company(project.getCompany() != null ? companyMapper.toDTO(project.getCompany()) : null)
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

