package com.example.demo.dto;

import com.example.demo.domain.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    Long id;
    
    @NotBlank(message = "Tên task không được để trống")
    String name;
    
    LocalDate startTime;
    LocalDate endTime;
    String description;
    Integer priority;
    Integer status;
    
    @NotNull(message = "Person ID không được để trống")
    Long personId;
    String personName;
    
    @NotNull(message = "Project ID không được để trống")
    Long projectId;
    String projectName;

    /**
     * Constructor cho HQL queries
     */
    public TaskDto(Long id, String name, LocalDate startTime, LocalDate endTime, String description, Integer priority, Integer status, Long personId, String personName, Long projectId, String projectName, boolean forHql) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.personId = personId;
        this.personName = personName;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    /**
     * Chuyển đổi DTO thành Entity
     */
    public Task toEntity() {
        return Task.builder()
                .id(this.id)
                .name(this.name)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .description(this.description)
                .priority(this.priority)
                .status(this.status)
                .build();
    }

    /**
     * Tạo DTO từ Entity
     */
    public static TaskDto fromEntity(Task task) {
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
                .personId(task.getPerson() != null ? task.getPerson().getId() : null)
                .personName(task.getPerson() != null ? task.getPerson().getFullName() : null)
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .projectName(task.getProject() != null ? task.getProject().getName() : null)
                .build();
    }
}
