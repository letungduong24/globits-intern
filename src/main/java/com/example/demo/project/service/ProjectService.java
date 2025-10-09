package com.example.demo.project.service;

import com.example.demo.project.dto.*;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    List<ResponseProjectDto> getAllProjects();
    PagedResponse<ResponseProjectDto> getAllProjects(Pageable pageable);
    ResponseProjectDto getProjectById(Long id);
    ResponseProjectDto getProjectByCode(String code);
    List<ResponseProjectDto> getProjectsByName(String name);
    List<BasicProjectDto> getProjectsByCompanyId(Long companyId);
    ResponseProjectDto createProject(CreateProjectDto projectDto);
    ResponseProjectDto updateProject(UpdateProjectDto projectDto);
    ResponseProjectDto deleteProject(Long id);
    ResponseProjectDto assignCompany(AssignCompanyDto dto);
    ResponseProjectDto assignPerson(AssignPersonDto dto);
    ResponseProjectDto removePerson(AssignPersonDto dto);
    boolean existsProjectById(Long id);
    boolean existsProjectByCode(String code);
}

