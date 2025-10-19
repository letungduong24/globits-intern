package com.example.demo.service.impl;

import com.example.demo.repositories.CompanyRepository;
import com.example.demo.service.DepartmentService;
import com.example.demo.domain.Company;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.department.dto.*;
import com.example.demo.department.dto.request.AssignCompanyDto;
import com.example.demo.department.dto.request.AssignParentDto;
import com.example.demo.department.dto.request.CreateDepartmentDto;
import com.example.demo.department.dto.request.UpdateDepartmentDto;
import com.example.demo.department.dto.response.BasicDepartmentDto;
import com.example.demo.department.dto.response.DepartmentDto;
import com.example.demo.domain.Department;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, 
                                  CompanyRepository companyRepository,
                                  DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentMapper.toDTOs(departmentRepository.findAll());
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
        return departmentMapper.toDTO(department);
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Department department = departmentRepository.findByCode(code);
        if (department == null) {
            throw new ResourceNotFoundException("Không tìm thấy phòng ban");
        }
        return departmentMapper.toDTO(department);
    }

    @Override
    public List<BasicDepartmentDto> GetAllDepartmentsByCompanyId(Long id) {
        List<Department> departments = departmentRepository.findByCompanyId(id);
        return departmentMapper.toBasicDTOs(departments);
    }

    @Override
    public List<BasicDepartmentDto> getAllDepartmentsByParentsId(Long id) {
        List<Department> departments = departmentRepository.findByParentId(id);
        return departmentMapper.toBasicDTOs(departments);
    }

    @Override
    public DepartmentDto createDepartment(CreateDepartmentDto departmentDto) {
        if (departmentRepository.existsByCode(departmentDto.getCode())) {
            throw new DuplicateResourceException("Mã phòng ban đã tồn tại");
        }

        Department department = departmentMapper.toEntity(departmentDto);

        // Set company if provided
        if (departmentDto.getCompanyId() != null) {
            Company company = companyRepository.findById(departmentDto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
            department.setCompany(company);
        }

        // Set parent if provided
        if (departmentDto.getParentId() != null) {
            Department parent = departmentRepository.findById(departmentDto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban cha"));
            department.setParent(parent);
        }

        Department saved = departmentRepository.save(department);
        return departmentMapper.toDTO(saved);
    }

    @Override
    public DepartmentDto updateDepartment(UpdateDepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));

        if (departmentDto.getCode() != null && !departmentDto.getCode().equals(department.getCode())) {
            if (departmentRepository.existsByCode(departmentDto.getCode())) {
                throw new DuplicateResourceException("Mã phòng ban đã tồn tại");
            }
        }

        departmentMapper.updateEntity(department, departmentDto);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toDTO(saved);
    }

    @Override
    public DepartmentDto deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));

        DepartmentDto dto = departmentMapper.toDTO(department);
        departmentRepository.delete(department);
        return dto;
    }

    @Override
    public DepartmentDto assignCompany(AssignCompanyDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        
        if (department.getCompany() != null && department.getCompany().getId().equals(dto.getCompanyId())) {
            throw new DuplicateResourceException("Phòng ban đã thuộc công ty này");
        }
        
        department.setCompany(company);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toDTO(saved);
    }

    @Override
    public DepartmentDto assignParent(AssignParentDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
        Department parent = departmentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban cha"));
        
        if (department.getParent() != null && department.getParent().getId().equals(dto.getParentId())) {
            throw new DuplicateResourceException("Phòng ban đã có phòng ban cha này");
        }
        
        if (dto.getDepartmentId().equals(dto.getParentId())) {
            throw new DuplicateResourceException("Phòng ban không thể là cha của chính nó");
        }
        
        department.setParent(parent);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toDTO(saved);
    }
}

