package com.example.demo.rest;

import com.example.demo.dto.RoleDto;
import com.example.demo.service.RoleService;
import com.example.demo.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RestRoleController {

    private final RoleService roleService;

    public RestRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<RoleDto>>> getRoles(){
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<RoleDto>>builder()
                        .success(true)
                        .message("Lấy roles thành công")
                        .data(roles)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> getRoleById(@PathVariable Long id){
        RoleDto role = roleService.getRoleById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleDto>builder()
                        .success(true)
                        .message("Lấy role thành công")
                        .data(role)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<RoleDto>> getRoleByRoleName(@PathVariable String name){
        RoleDto role = roleService.getRoleByRoleName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleDto>builder()
                        .success(true)
                        .message("Lấy role thành công")
                        .data(role)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@Valid @RequestBody RoleDto createRoleDto){
        RoleDto created = roleService.createRole(createRoleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RoleDto>builder()
                        .success(true)
                        .message("Tạo role thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<RoleDto>> updateRole(@Valid @RequestBody RoleDto updateRoleDto){
        RoleDto updated = roleService.updateRole(updateRoleDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleDto>builder()
                        .success(true)
                        .message("Cập nhật role thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> deleteRoleById(@PathVariable Long id) {
        RoleDto deleted = roleService.deleteRoleById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RoleDto>builder()
                        .success(true)
                        .message("Xóa role thành công")
                        .data(deleted)
                        .build());
    }
}