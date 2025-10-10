package com.example.demo.task;
import com.example.demo.shared.response.ApiResponse;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.task.dto.request.CreateTaskDto;
import com.example.demo.task.dto.response.TaskDto;
import com.example.demo.task.service.TaskService;
import com.example.demo.task.specification.TaskSpecificationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasks(){
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<TaskDto>>builder()
                        .success(true)
                        .message("Lây tasks thành công")
                        .data(tasks)
                        .build());
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<PagedResponse<TaskDto>>> getTasksPaged(
            TaskSpecificationRequest taskSpecificationRequest
    ){
        PagedResponse<TaskDto> pagedTask = taskService.getAllTasks(taskSpecificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PagedResponse<TaskDto>>builder()
                        .success(true)
                        .message("Lây tasks thành công")
                        .data(pagedTask)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@Valid @RequestBody CreateTaskDto taskDto){
        TaskDto created = taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TaskDto>builder()
                        .success(true)
                        .message("Tạo công ty thành công")
                        .data(created)
                        .build());
    }
}
