package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments();
    DepartmentDto getDepartmentById(Long id);
    DepartmentDto getDepartmentByCode(String code);
    List<DepartmentDto> getAllDepartmentsByCompanyId(Long id);
    List<DepartmentDto> getAllDepartmentsByParentsId(Long id);
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto updateDepartment(DepartmentDto departmentDto);
    DepartmentDto deleteDepartment(Long id);
    DepartmentDto assignCompany(Long departmentId, Long companyId);
    DepartmentDto assignParent(Long departmentId, Long parentId);
}
