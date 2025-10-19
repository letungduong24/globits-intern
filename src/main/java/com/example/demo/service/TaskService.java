package com.example.demo.service;

import com.example.demo.shared.request.PaginationRequest;
import com.example.demo.task.dto.request.*;
import com.example.demo.task.dto.response.TaskDto;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.task.specification.TaskSpecificationRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    PagedResponse<TaskDto> getAllTasks(TaskSpecificationRequest taskSpecificationRequest, PaginationRequest paginationRequest);
    TaskDto getTaskById(Long id);
    List<TaskDto> getTasksByPersonId(Long personId);
    List<TaskDto> getTasksByProjectId(Long projectId);
    TaskDto createTask(CreateTaskDto taskDto);
    TaskDto updateTask(UpdateTaskDto taskDto);
    TaskDto deleteTask(Long id);
    TaskDto assignPerson(AssignPerson dto);
    TaskDto assignProject(AssignProject dto);
    TaskDto removePerson(AssignPerson dto);
    TaskDto removeProject(AssignProject dto);
    Resource exportTasksToExcel();
}