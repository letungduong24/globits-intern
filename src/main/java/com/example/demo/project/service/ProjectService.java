package com.example.demo.project.service;

import com.example.demo.project.dto.request.*;
import com.example.demo.project.dto.response.BasicProjectDto;
import com.example.demo.project.dto.response.ProjectDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    List<BasicProjectDto> getAllProjects();
    PagedResponse<BasicProjectDto> getAllProjects(Pageable pageable);
    ProjectDto getProjectById(Long id);
    ProjectDto getProjectByCode(String code);
    List<BasicProjectDto> getProjectsByName(String name);
    List<BasicProjectDto> getProjectsByCompanyId(Long companyId);
    ProjectDto createProject(CreateProjectDto projectDto);
    ProjectDto updateProject(UpdateProjectDto projectDto);
    ProjectDto deleteProject(Long id);
    ProjectDto assignCompany(AssignCompanyDto dto);
    ProjectDto assignPerson(AssignPersonDto dto);
    ProjectDto removePerson(AssignPersonDto dto);
}

