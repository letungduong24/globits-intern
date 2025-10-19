package com.example.demo.task.dto;

import com.example.demo.person.dto.PersonMapper;
import com.example.demo.project.dto.ProjectMapper;
import com.example.demo.task.dto.request.CreateTaskDto;
import com.example.demo.task.dto.request.UpdateTaskDto;
import com.example.demo.domain.Task;
import com.example.demo.task.dto.response.TaskDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private final PersonMapper personMapper;
    private  final ProjectMapper projectMapper;

    public TaskMapper(
            PersonMapper personMapper,
            ProjectMapper projectMapper
    ) {
        this.personMapper = personMapper;
        this.projectMapper = projectMapper;
    }

    public TaskDto toDTO(Task task) {
        if (task == null) {
            return null;
        }

        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .startTime(task.getStartTime())
                .endTime(task.getEndTime())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .person(task.getPerson() != null ? personMapper.toDTO(task.getPerson()) : null)
                .project(task.getProject() != null ? projectMapper.toBasicDTO(task.getProject()) : null)
                .build();
    }

    public List<TaskDto> toDTOs(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }

        return tasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Set<TaskDto> toDTOs(Set<Task> tasks) {
        if (tasks == null) {
            return null;
        }

        return tasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Task toEntity(CreateTaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        return Task.builder()
                .name(taskDto.getName())
                .startTime(taskDto.getStartTime())
                .endTime(taskDto.getEndTime())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .status(taskDto.getStatus())
                .build();
    }

    public Task updateEntity(Task existingTask, UpdateTaskDto updateDto) {
        if (existingTask == null || updateDto == null) {
            return existingTask;
        }

        if (updateDto.getName() != null) {
            existingTask.setName(updateDto.getName());
        }
        if (updateDto.getStartTime() != null) {
            existingTask.setStartTime(updateDto.getStartTime());
        }
        if (updateDto.getEndTime() != null) {
            existingTask.setEndTime(updateDto.getEndTime());
        }
        if (updateDto.getDescription() != null) {
            existingTask.setDescription(updateDto.getDescription());
        }
        if (updateDto.getPriority() != null) {
            existingTask.setPriority(updateDto.getPriority());
        }
        if (updateDto.getStatus() != null) {
            existingTask.setStatus(updateDto.getStatus());
        }

        return existingTask;
    }
}