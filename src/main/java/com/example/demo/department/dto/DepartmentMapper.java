package com.example.demo.department.dto;

import com.example.demo.department.dto.request.CreateDepartmentDto;
import com.example.demo.department.dto.request.UpdateDepartmentDto;
import com.example.demo.department.dto.response.BasicDepartmentDto;
import com.example.demo.department.dto.response.DepartmentDto;
import com.example.demo.domain.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    public DepartmentDto toDTO(Department department) {
        if (department == null) {
            return null;
        }
        
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .companyId(department.getCompany() != null ? department.getCompany().getId() : null)
                .parentId(department.getParent() != null ? department.getParent().getId() : null)
                .build();
    }

    public List<DepartmentDto> toDTOs(List<Department> departments) {
        if (departments == null) {
            return null;
        }
        
        return departments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BasicDepartmentDto toBasicDTO(Department department) {
        if (department == null) {
            return null;
        }
        
        return BasicDepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .build();
    }

    public List<BasicDepartmentDto> toBasicDTOs(List<Department> departments) {
        if (departments == null) {
            return null;
        }
        
        return departments.stream()
                .map(this::toBasicDTO)
                .collect(Collectors.toList());
    }

    public Department toEntity(CreateDepartmentDto departmentDto) {
        if (departmentDto == null) {
            return null;
        }
        
        return Department.builder()
                .name(departmentDto.getName())
                .code(departmentDto.getCode())
                .build();
    }

    public Department updateEntity(Department existingDepartment, UpdateDepartmentDto updateDto) {
        if (updateDto.getName() != null) {
            existingDepartment.setName(updateDto.getName());
        }
        if (updateDto.getCode() != null) {
            existingDepartment.setCode(updateDto.getCode());
        }
        
        return existingDepartment;
    }
}
