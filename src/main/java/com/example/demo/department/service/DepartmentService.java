package com.example.demo.department.service;

import com.example.demo.department.dto.*;
import com.example.demo.department.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<ResponseDepartmentDto> getAllDepartments();
    ResponseDepartmentDto getDepartmentById(Long id);
    ResponseDepartmentDto getDepartmentByCode(String code);
    List<BasicDepartmentDto> GetAllDepartmentsByCompanyId(Long id);
    List<BasicDepartmentDto> getAllDepartmentsByParentsId(Long id);
    ResponseDepartmentDto createDepartment(CreateDepartmentDto departmentDto);
    ResponseDepartmentDto updateDepartment(UpdateDepartmentDto departmentDto);
    ResponseDepartmentDto deleteDepartment(Long id);
    ResponseDepartmentDto assignCompany(AssignCompanyDto dto);
    ResponseDepartmentDto assignParent(AssignParentDto dto);
}
