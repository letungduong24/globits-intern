package com.example.demo.rest;

import com.example.demo.project.dto.request.*;
import com.example.demo.project.dto.response.BasicProjectDto;
import com.example.demo.project.dto.response.ProjectDto;
import com.example.demo.service.ProjectService;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.ApiResponse;
import com.example.demo.shared.response.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class RestProjectController {

    private final ProjectService projectService;

    public RestProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<BasicProjectDto>>> getProjects(){
        List<BasicProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BasicProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects thành công")
                        .data(projects)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<BasicProjectDto>>> getProjectsPaged(
            PaginationRequest paginationRequest
    ){
        Pageable pageable = paginationRequest.toPageable();
        
        PagedResponse<BasicProjectDto> pagedProjects = projectService.getAllProjects(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PagedResponse<BasicProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects phân trang thành công")
                        .data(pagedProjects)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ProjectDto>> createProject(@Valid @RequestBody CreateProjectDto project){
        ProjectDto created = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Tạo dự án thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ProjectDto>> updateProject(@Valid @RequestBody UpdateProjectDto projectDto){
        ProjectDto updated = projectService.updateProject(projectDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Cập nhật dự án thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDto>> deleteProjectById(@PathVariable Long id) {
        ProjectDto deleted = projectService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Xóa dự án thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDto>> getProjectById(@PathVariable Long id){
        ProjectDto project = projectService.getProjectById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Lấy project thành công")
                        .data(project)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<BasicProjectDto>>> getProjectsByName(@PathVariable String name){
        List<BasicProjectDto> projects = projectService.getProjectsByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BasicProjectDto>>builder()
                        .success(true)
                        .message("Lấy projects thành công")
                        .data(projects)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<ProjectDto>> getProjectByCode(@PathVariable String code){
        ProjectDto project = projectService.getProjectByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
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
    public ResponseEntity<ApiResponse<ProjectDto>> assignCompany(@Valid @RequestBody AssignCompanyDto assignCompanyDto){
        ProjectDto assigned = projectService.assignCompany(assignCompanyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Gán công ty cho dự án thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/assign-person")
    public ResponseEntity<ApiResponse<ProjectDto>> assignPerson(@Valid @RequestBody AssignPersonDto assignPersonDto){
        ProjectDto assigned = projectService.assignPerson(assignPersonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Gán person cho dự án thành công")
                        .data(assigned)
                        .build());
    }

    @PutMapping("/remove-person")
    public ResponseEntity<ApiResponse<ProjectDto>> removePerson(@Valid @RequestBody AssignPersonDto assignPersonDto){
        ProjectDto removed = projectService.removePerson(assignPersonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProjectDto>builder()
                        .success(true)
                        .message("Xóa person khỏi dự án thành công")
                        .data(removed)
                        .build());
    }
}

