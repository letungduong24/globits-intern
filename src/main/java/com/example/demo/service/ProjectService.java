package com.example.demo.service;

import com.example.demo.dto.ProjectDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects();
    PagedResponse<ProjectDto> getAllProjects(Pageable pageable);
    ProjectDto getProjectById(Long id);
    ProjectDto getProjectByCode(String code);
    List<ProjectDto> getProjectsByName(String name);
    List<ProjectDto> getProjectsByCompanyId(Long companyId);
    ProjectDto createProject(ProjectDto projectDto);
    ProjectDto updateProject(ProjectDto projectDto);
    ProjectDto deleteProject(Long id);
    ProjectDto assignCompany(Long projectId, Long companyId);
    ProjectDto assignPerson(Long projectId, Long personId);
    ProjectDto removePerson(Long projectId, Long personId);
}

