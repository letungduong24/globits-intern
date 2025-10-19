package com.example.demo.service.impl;

import com.example.demo.repositories.CompanyRepository;
import com.example.demo.service.DepartmentService;
import com.example.demo.domain.Company;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.domain.Department;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, 
                                  CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAllAsDto();
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        return departmentRepository.findByCodeAsDto(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
    }

    @Override
    public List<DepartmentDto> getAllDepartmentsByCompanyId(Long id) {
        return departmentRepository.findByCompanyIdAsDto(id);
    }

    @Override
    public List<DepartmentDto> getAllDepartmentsByParentsId(Long id) {
        return departmentRepository.findByParentIdAsDto(id);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsByCode(departmentDto.getCode())) {
            throw new DuplicateResourceException("Mã phòng ban đã tồn tại");
        }

        Department department = departmentDto.toEntity();

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
        return DepartmentDto.fromEntity(saved);
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));

        if (departmentDto.getCode() != null && !departmentDto.getCode().equals(department.getCode())) {
            if (departmentRepository.existsByCode(departmentDto.getCode())) {
                throw new DuplicateResourceException("Mã phòng ban đã tồn tại");
            }
        }

        // Cập nhật các trường
        if (departmentDto.getName() != null) {
            department.setName(departmentDto.getName());
        }
        if (departmentDto.getCode() != null) {
            department.setCode(departmentDto.getCode());
        }
        
        Department saved = departmentRepository.save(department);
        return DepartmentDto.fromEntity(saved);
    }

    @Override
    public DepartmentDto deleteDepartment(Long id) {
        DepartmentDto dto = departmentRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
        
        departmentRepository.deleteById(id);
        return dto;
    }

    @Override
    public DepartmentDto assignCompany(Long departmentId, Long companyId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        
        if (department.getCompany() != null && department.getCompany().getId().equals(companyId)) {
            throw new DuplicateResourceException("Phòng ban đã thuộc công ty này");
        }
        
        department.setCompany(company);
        Department saved = departmentRepository.save(department);
        return DepartmentDto.fromEntity(saved);
    }

    @Override
    public DepartmentDto assignParent(Long departmentId, Long parentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban"));
        Department parent = departmentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng ban cha"));
        
        if (department.getParent() != null && department.getParent().getId().equals(parentId)) {
            throw new DuplicateResourceException("Phòng ban đã có phòng ban cha này");
        }
        
        if (departmentId.equals(parentId)) {
            throw new DuplicateResourceException("Phòng ban không thể là cha của chính nó");
        }
        
        department.setParent(parent);
        Department saved = departmentRepository.save(department);
        return DepartmentDto.fromEntity(saved);
    }
}

