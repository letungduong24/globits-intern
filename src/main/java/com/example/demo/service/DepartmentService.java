package com.example.demo.service;

import com.example.demo.department.dto.request.AssignCompanyDto;
import com.example.demo.department.dto.request.AssignParentDto;
import com.example.demo.department.dto.request.CreateDepartmentDto;
import com.example.demo.department.dto.request.UpdateDepartmentDto;
import com.example.demo.department.dto.response.BasicDepartmentDto;
import com.example.demo.department.dto.response.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments();
    DepartmentDto getDepartmentById(Long id);
    DepartmentDto getDepartmentByCode(String code);
    List<BasicDepartmentDto> GetAllDepartmentsByCompanyId(Long id);
    List<BasicDepartmentDto> getAllDepartmentsByParentsId(Long id);
    DepartmentDto createDepartment(CreateDepartmentDto departmentDto);
    DepartmentDto updateDepartment(UpdateDepartmentDto departmentDto);
    DepartmentDto deleteDepartment(Long id);
    DepartmentDto assignCompany(AssignCompanyDto dto);
    DepartmentDto assignParent(AssignParentDto dto);
}
