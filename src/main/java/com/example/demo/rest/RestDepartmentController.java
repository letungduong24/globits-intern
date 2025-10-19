package com.example.demo.rest;

import com.example.demo.department.dto.request.AssignCompanyDto;
import com.example.demo.department.dto.request.AssignParentDto;
import com.example.demo.department.dto.request.CreateDepartmentDto;
import com.example.demo.department.dto.request.UpdateDepartmentDto;
import com.example.demo.department.dto.response.BasicDepartmentDto;
import com.example.demo.department.dto.response.DepartmentDto;
import com.example.demo.service.DepartmentService;
import com.example.demo.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class RestDepartmentController {

    private final DepartmentService departmentService;

    public RestDepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getDepartments(){
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DepartmentDto>>builder()
                        .success(true)
                        .message("Lấy departments thành công")
                        .data(departments)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@Valid @RequestBody CreateDepartmentDto department){
        DepartmentDto created = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Tạo phòng ban thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(@Valid @RequestBody UpdateDepartmentDto departmentDto){
        DepartmentDto updated = departmentService.updateDepartment(departmentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Cập nhật phòng ban thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartmentById(@PathVariable Long id) {
        DepartmentDto deleted = departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Xóa phòng ban thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(@PathVariable Long id){
        DepartmentDto department = departmentService.getDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Lấy department thành công")
                        .data(department)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentByCode(@PathVariable String code){
        DepartmentDto department = departmentService.getDepartmentByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Lấy department thành công")
                        .data(department)
                        .build());
    }

    @GetMapping("/{companyId}/by-company")
    public ResponseEntity<ApiResponse<List<BasicDepartmentDto>>> getDepartmentsByCompanyId(@PathVariable Long companyId){
        List<BasicDepartmentDto> departments = departmentService.GetAllDepartmentsByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BasicDepartmentDto>>builder()
                        .success(true)
                        .message("Lấy departments theo công ty thành công")
                        .data(departments)
                        .build());
    }

    @GetMapping("/{parentId}/by-parent")
    public ResponseEntity<ApiResponse<List<BasicDepartmentDto>>> getDepartmentsByParentId(@PathVariable Long parentId){
        List<BasicDepartmentDto> departments = departmentService.getAllDepartmentsByParentsId(parentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BasicDepartmentDto>>builder()
                        .success(true)
                        .message("Lấy departments con thành công")
                        .data(departments)
                        .build());
    }

    @PutMapping("/assign-company")
    public ResponseEntity<ApiResponse<DepartmentDto>> assignCompany(@Valid @RequestBody AssignCompanyDto assignCompanyDto){
        DepartmentDto assigned = departmentService.assignCompany(assignCompanyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Gán công ty cho phòng ban thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/assign-parent")
    public ResponseEntity<ApiResponse<DepartmentDto>> assignParent(@Valid @RequestBody AssignParentDto assignParentDto){
        DepartmentDto assigned = departmentService.assignParent(assignParentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Gán phòng ban cha thành công")
                        .data(assigned)
                        .build());
    }
}

