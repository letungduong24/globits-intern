package com.example.demo.service;

import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.dto.TaskDto;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.specification.request.TaskSpecificationRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    PagedResponse<TaskDto> getAllTasks(TaskSpecificationRequest taskSpecificationRequest, PaginationRequest paginationRequest);
    TaskDto getTaskById(Long id);
    List<TaskDto> getTasksByPersonId(Long personId);
    List<TaskDto> getTasksByProjectId(Long projectId);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(TaskDto taskDto);
    TaskDto deleteTask(Long id);
    TaskDto assignPerson(Long taskId, Long personId);
    TaskDto assignProject(Long taskId, Long projectId);
    TaskDto removePerson(Long taskId, Long personId);
    TaskDto removeProject(Long taskId, Long projectId);
    Resource exportTasksToExcel();
}