package com.example.demo.project.service;

import com.example.demo.company.CompanyRepository;
import com.example.demo.company.entity.Company;
import com.example.demo.person.PersonRepository;
import com.example.demo.person.entity.Person;
import com.example.demo.project.ProjectRepository;
import com.example.demo.project.dto.*;
import com.example.demo.project.dto.request.*;
import com.example.demo.project.dto.response.BasicProjectDto;
import com.example.demo.project.dto.response.ProjectDto;
import com.example.demo.project.entity.Project;
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
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, 
                               CompanyRepository companyRepository,
                               PersonRepository personRepository,
                               ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<BasicProjectDto> getAllProjects() {
        return projectMapper.toBasicDTOs(projectRepository.findAll());
    }

    @Override
    public PagedResponse<BasicProjectDto> getAllProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);
        List<BasicProjectDto> contentMapped = projectMapper.toBasicDTOs(projectPage.getContent());
        
        return PagedUtil.ToPagedResponse(projectPage, contentMapped);
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        return projectMapper.toDTO(project);
    }

    @Override
    public ProjectDto getProjectByCode(String code) {
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        return projectMapper.toDTO(project);
    }

    @Override
    public List<BasicProjectDto> getProjectsByName(String name) {
        return projectMapper.toBasicDTOs(projectRepository.findByName(name));
    }

    @Override
    public List<BasicProjectDto> getProjectsByCompanyId(Long companyId) {
        return projectMapper.toBasicDTOs(projectRepository.findByCompanyId(companyId));
    }

    @Override
    public ProjectDto createProject(CreateProjectDto projectDto) {
        if (projectRepository.existsByCode(projectDto.getCode())) {
            throw new DuplicateResourceException("Mã dự án đã tồn tại");
        }

        Project project = projectMapper.toEntity(projectDto);

        // Set company if provided
        if (projectDto.getCompanyId() != null) {
            Company company = companyRepository.findById(projectDto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
            project.setCompany(company);
        }

        Project saved = projectRepository.save(project);
        return projectMapper.toDTO(saved);
    }

    @Override
    public ProjectDto updateProject(UpdateProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));

        if (projectDto.getCode() != null && !projectDto.getCode().equals(project.getCode())) {
            if (projectRepository.existsByCode(projectDto.getCode())) {
                throw new DuplicateResourceException("Mã dự án đã tồn tại");
            }
        }

        projectMapper.updateEntity(project, projectDto);
        Project saved = projectRepository.save(project);
        return projectMapper.toDTO(saved);
    }

    @Override
    public ProjectDto deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));

        ProjectDto dto = projectMapper.toDTO(project);
        projectRepository.delete(project);
        return dto;
    }

    @Override
    public ProjectDto assignCompany(AssignCompanyDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));

        if (project.getCompany() != null && project.getCompany().getId().equals(dto.getCompanyId())) {
            throw new DuplicateResourceException("Dự án đã thuộc công ty này");
        }

        project.setCompany(company);
        Project saved = projectRepository.save(project);
        return projectMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ProjectDto assignPerson(AssignPersonDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        Person person = personRepository.findById(dto.getPersonId())
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
        return projectMapper.toDTO(saved);
    }

    @Override
    public ProjectDto removePerson(AssignPersonDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dự án"));
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy person"));

        if (project.getPersons() == null || !project.getPersons().contains(person)) {
            throw new ResourceNotFoundException("Person không thuộc dự án này");
        }

        project.getPersons().remove(person);
        Project saved = projectRepository.save(project);
        return projectMapper.toDTO(saved);
    }
}

