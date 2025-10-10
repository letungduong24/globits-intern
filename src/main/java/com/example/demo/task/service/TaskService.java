package com.example.demo.task.service;

import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.task.dto.request.CreateTaskDto;
import com.example.demo.task.dto.response.TaskDto;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.task.specification.TaskSpecificationRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    PagedResponse<TaskDto> getAllTasks(TaskSpecificationRequest taskSpecificationRequest, PaginationRequest paginationRequest);
    TaskDto createTask(CreateTaskDto taskDto);

}