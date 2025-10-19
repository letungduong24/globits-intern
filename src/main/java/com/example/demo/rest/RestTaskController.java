package com.example.demo.rest;
import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.shared.response.ApiResponse;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.dto.TaskDto;
import com.example.demo.service.TaskService;
import com.example.demo.specification.request.TaskSpecificationRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class RestTaskController {
    private final TaskService taskService;

    public RestTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasks(){
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TaskDto>>builder()
                        .success(true)
                        .message("Lấy tasks thành công")
                        .data(tasks)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<TaskDto>>> getTasksPaged(
            TaskSpecificationRequest taskSpecificationRequest,
            PaginationRequest paginationRequest
    ) {
        PagedResponse<TaskDto> pagedTask = taskService.getAllTasks(taskSpecificationRequest, paginationRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PagedResponse<TaskDto>>builder()
                        .success(true)
                        .message("Lấy tasks thành công")
                        .data(pagedTask)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Lấy task thành công")
                        .data(task)
                        .build());
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByPersonId(@PathVariable Long personId) {
        List<TaskDto> tasks = taskService.getTasksByPersonId(personId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TaskDto>>builder()
                        .success(true)
                        .message("Lấy tasks theo person thành công")
                        .data(tasks)
                        .build());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByProjectId(@PathVariable Long projectId) {
        List<TaskDto> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TaskDto>>builder()
                        .success(true)
                        .message("Lấy tasks theo project thành công")
                        .data(tasks)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@Valid @RequestBody TaskDto taskDto){
        TaskDto created = taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Tạo task thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto updated = taskService.updateTask(taskDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Cập nhật task thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> deleteTask(@PathVariable Long id) {
        TaskDto deleted = taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Xóa task thành công")
                        .data(deleted)
                        .build());
    }

    @PostMapping("/{taskId}/assign-person/{personId}")
    public ResponseEntity<ApiResponse<TaskDto>> assignPerson(@PathVariable Long taskId, @PathVariable Long personId) {
        TaskDto updated = taskService.assignPerson(taskId, personId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Gán person cho task thành công")
                        .data(updated)
                        .build());
    }

    @PostMapping("/{taskId}/assign-project/{projectId}")
    public ResponseEntity<ApiResponse<TaskDto>> assignProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        TaskDto updated = taskService.assignProject(taskId, projectId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Gán project cho task thành công")
                        .data(updated)
                        .build());
    }

    @PostMapping("/{taskId}/remove-person/{personId}")
    public ResponseEntity<ApiResponse<TaskDto>> removePerson(@PathVariable Long taskId, @PathVariable Long personId) {
        TaskDto updated = taskService.removePerson(taskId, personId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Gỡ person khỏi task thành công")
                        .data(updated)
                        .build());
    }

    @PostMapping("/{taskId}/remove-project/{projectId}")
    public ResponseEntity<ApiResponse<TaskDto>> removeProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        TaskDto updated = taskService.removeProject(taskId, projectId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Gỡ project khỏi task thành công")
                        .data(updated)
                        .build());
    }

    @GetMapping("/export/excel")
    public ResponseEntity<Resource> exportTasksToExcel() {
        Resource resource = taskService.exportTasksToExcel();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tasks_export.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
