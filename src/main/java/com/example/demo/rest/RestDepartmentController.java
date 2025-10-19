package com.example.demo.rest;

import com.example.demo.dto.DepartmentDto;
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
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@Valid @RequestBody DepartmentDto department){
        DepartmentDto created = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Tạo phòng ban thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(@Valid @RequestBody DepartmentDto departmentDto){
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
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getDepartmentsByCompanyId(@PathVariable Long companyId){
        List<DepartmentDto> departments = departmentService.getAllDepartmentsByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DepartmentDto>>builder()
                        .success(true)
                        .message("Lấy departments theo công ty thành công")
                        .data(departments)
                        .build());
    }

    @GetMapping("/{parentId}/by-parent")
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getDepartmentsByParentId(@PathVariable Long parentId){
        List<DepartmentDto> departments = departmentService.getAllDepartmentsByParentsId(parentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DepartmentDto>>builder()
                        .success(true)
                        .message("Lấy departments con thành công")
                        .data(departments)
                        .build());
    }

    @PutMapping("/{departmentId}/assign-company/{companyId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> assignCompany(@PathVariable Long departmentId, @PathVariable Long companyId){
        DepartmentDto assigned = departmentService.assignCompany(departmentId, companyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Gán công ty cho phòng ban thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/{departmentId}/assign-parent/{parentId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> assignParent(@PathVariable Long departmentId, @PathVariable Long parentId){
        DepartmentDto assigned = departmentService.assignParent(departmentId, parentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DepartmentDto>builder()
                        .success(true)
                        .message("Gán phòng ban cha thành công")
                        .data(assigned)
                        .build());
    }
}

