package com.example.demo.service.impl;

import com.example.demo.repositories.CompanyRepository;
import com.example.demo.domain.Company;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.domain.Person;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.dto.ProjectDto;
import com.example.demo.domain.Project;
import com.example.demo.service.ProjectService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, 
                               CompanyRepository companyRepository,
                               PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAllAsDto();
    }

    @Override
    public PagedResponse<ProjectDto> getAllProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);
        List<ProjectDto> contentMapped = projectRepository.findAllAsDto();
        
        return PagedUtil.ToPagedResponse(projectPage, contentMapped);
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        return projectRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
    }

    @Override
    public ProjectDto getProjectByCode(String code) {
        return projectRepository.findByCodeAsDto(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
    }

    @Override
    public List<ProjectDto> getProjectsByName(String name) {
        return projectRepository.findByNameAsDto(name);
    }

    @Override
    public List<ProjectDto> getProjectsByCompanyId(Long companyId) {
        return projectRepository.findByCompanyIdAsDto(companyId);
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        if (projectRepository.existsByCode(projectDto.getCode())) {
            throw new DuplicateResourceException("Mã dự án đã tồn tại");
        }

        Project project = projectDto.toEntity();

        // Set company if provided
        if (projectDto.getCompanyId() != null) {
            Company company = companyRepository.findById(projectDto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
            project.setCompany(company);
        }

        Project saved = projectRepository.save(project);
        return ProjectDto.fromEntity(saved);
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));

        if (projectDto.getCode() != null && !projectDto.getCode().equals(project.getCode())) {
            if (projectRepository.existsByCode(projectDto.getCode())) {
                throw new DuplicateResourceException("Mã dự án đã tồn tại");
            }
        }

        // Cập nhật các trường
        if (projectDto.getName() != null) {
            project.setName(projectDto.getName());
        }
        if (projectDto.getCode() != null) {
            project.setCode(projectDto.getCode());
        }
        if (projectDto.getDescription() != null) {
            project.setDescription(projectDto.getDescription());
        }
        
        Project saved = projectRepository.save(project);
        return ProjectDto.fromEntity(saved);
    }

    @Override
    public ProjectDto deleteProject(Long id) {
        ProjectDto dto = projectRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        
        projectRepository.deleteById(id);
        return dto;
    }

    @Override
    public ProjectDto assignCompany(Long projectId, Long companyId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));

        if (project.getCompany() != null && project.getCompany().getId().equals(companyId)) {
            throw new DuplicateResourceException("Dự án đã thuộc công ty này");
        }

        project.setCompany(company);
        Project saved = projectRepository.save(project);
        return ProjectDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public ProjectDto assignPerson(Long projectId, Long personId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        if (project.getCompany() == null) {
            throw new ResourceNotFoundException("Dự án chưa được gán công ty");
        }

        if (person.getCompany() == null) {
            throw new ResourceNotFoundException("Person chưa được gán công ty");
        }

        if (!project.getCompany().getId().equals(person.getCompany().getId())) {
            throw new DuplicateResourceException("Person không thuộc công ty của dự án");
        }

        if (project.getPersons().contains(person)) {
            throw new DuplicateResourceException("Person đã được gán cho dự án này");
        }

        project.getPersons().add(person);
        Project saved = projectRepository.save(project);
        return ProjectDto.fromEntity(saved);
    }

    @Override
    public ProjectDto removePerson(Long projectId, Long personId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        if (project.getPersons() == null || !project.getPersons().contains(person)) {
            throw new ResourceNotFoundException("Person không thuộc dự án này");
        }

        project.getPersons().remove(person);
        Project saved = projectRepository.save(project);
        return ProjectDto.fromEntity(saved);
    }
}

