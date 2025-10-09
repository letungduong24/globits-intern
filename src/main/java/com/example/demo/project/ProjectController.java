package com.example.demo.project;

import com.example.demo.project.dto.*;
import com.example.demo.project.service.ProjectService;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.ApiResponse;
import com.example.demo.shared.response.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ResponseProjectDto>>> getProjects(){
        List<ResponseProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects thành công")
                        .data(projects)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<ResponseProjectDto>>> getProjectsPaged(
            PaginationRequest paginationRequest
    ){
        Pageable pageable = paginationRequest.toPageable();
        
        PagedResponse<ResponseProjectDto> pagedProjects = projectService.getAllProjects(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PagedResponse<ResponseProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects phân trang thành công")
                        .data(pagedProjects)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResponseProjectDto>> createProject(@Valid @RequestBody CreateProjectDto project){
        ResponseProjectDto created = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Tạo dự án thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponseProjectDto>> updateProject(@Valid @RequestBody UpdateProjectDto projectDto){
        ResponseProjectDto updated = projectService.updateProject(projectDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Cập nhật dự án thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseProjectDto>> deleteProjectById(@PathVariable Long id) {
        ResponseProjectDto deleted = projectService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Xóa dự án thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseProjectDto>> getProjectById(@PathVariable Long id){
        ResponseProjectDto project = projectService.getProjectById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Lấy project thành công")
                        .data(project)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<ResponseProjectDto>>> getProjectsByName(@PathVariable String name){
        List<ResponseProjectDto> projects = projectService.getProjectsByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects thành công")
                        .data(projects)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<ResponseProjectDto>> getProjectByCode(@PathVariable String code){
        ResponseProjectDto project = projectService.getProjectByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Lấy project thành công")
                        .data(project)
                        .build());
    }

    @GetMapping("/{companyId}/by-company")
    public ResponseEntity<ApiResponse<List<BasicProjectDto>>> getProjectsByCompanyId(@PathVariable Long companyId){
        List<BasicProjectDto> projects = projectService.getProjectsByCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BasicProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects theo công ty thành công")
                        .data(projects)
                        .build());
    }

    @PutMapping("/assign-company")
    public ResponseEntity<ApiResponse<ResponseProjectDto>> assignCompany(@Valid @RequestBody AssignCompanyDto assignCompanyDto){
        ResponseProjectDto assigned = projectService.assignCompany(assignCompanyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Gán công ty cho dự án thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/assign-person")
    public ResponseEntity<ApiResponse<ResponseProjectDto>> assignPerson(@Valid @RequestBody AssignPersonDto assignPersonDto){
        ResponseProjectDto assigned = projectService.assignPerson(assignPersonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Gán person cho dự án thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/remove-person")
    public ResponseEntity<ApiResponse<ResponseProjectDto>> removePerson(@Valid @RequestBody AssignPersonDto assignPersonDto){
        ResponseProjectDto removed = projectService.removePerson(assignPersonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseProjectDto>builder()
                        .success(true)
                        .message("Xóa person khỏi dự án thành công")
                        .data(removed)
                        .build());
    }
}

