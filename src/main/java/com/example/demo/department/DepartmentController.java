package com.example.demo.department;

import com.example.demo.department.dto.*;
import com.example.demo.department.service.DepartmentService;
import com.example.demo.shared.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ResponseDepartmentDto>>> getDepartments(){
        List<ResponseDepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseDepartmentDto>>builder()
                        .success(true)
                        .message("Lấy departments thành công")
                        .data(departments)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> createDepartment(@Valid @RequestBody CreateDepartmentDto department){
        ResponseDepartmentDto created = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
                        .success(true)
                        .message("Tạo phòng ban thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> updateDepartment(@Valid @RequestBody UpdateDepartmentDto departmentDto){
        ResponseDepartmentDto updated = departmentService.updateDepartment(departmentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
                        .success(true)
                        .message("Cập nhật phòng ban thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> deleteDepartmentById(@PathVariable Long id) {
        ResponseDepartmentDto deleted = departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
                        .success(true)
                        .message("Xóa phòng ban thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> getDepartmentById(@PathVariable Long id){
        ResponseDepartmentDto department = departmentService.getDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
                        .success(true)
                        .message("Lấy department thành công")
                        .data(department)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> getDepartmentByCode(@PathVariable String code){
        ResponseDepartmentDto department = departmentService.getDepartmentByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
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
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> assignCompany(@Valid @RequestBody AssignCompanyDto assignCompanyDto){
        ResponseDepartmentDto assigned = departmentService.assignCompany(assignCompanyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
                        .success(true)
                        .message("Gán công ty cho phòng ban thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/assign-parent")
    public ResponseEntity<ApiResponse<ResponseDepartmentDto>> assignParent(@Valid @RequestBody AssignParentDto assignParentDto){
        ResponseDepartmentDto assigned = departmentService.assignParent(assignParentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseDepartmentDto>builder()
                        .success(true)
                        .message("Gán phòng ban cha thành công")
                        .data(assigned)
                        .build());
    }
}

