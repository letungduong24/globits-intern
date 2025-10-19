package com.example.demo.repositories;

import com.example.demo.domain.Task;
import com.example.demo.dto.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.TaskDto(t.id, t.name, t.startTime, t.endTime, t.description, t.priority, t.status, t.person.id, t.person.fullName, t.project.id, t.project.name, true) FROM Task t WHERE t.person.id = :personId")
    List<TaskDto> findByPersonIdAsDto(@Param("personId") Long personId);
    
    @Query("SELECT new com.example.demo.dto.TaskDto(t.id, t.name, t.startTime, t.endTime, t.description, t.priority, t.status, t.person.id, t.person.fullName, t.project.id, t.project.name, true) FROM Task t WHERE t.project.id = :projectId")
    List<TaskDto> findByProjectIdAsDto(@Param("projectId") Long projectId);
    
    @Query("SELECT new com.example.demo.dto.TaskDto(t.id, t.name, t.startTime, t.endTime, t.description, t.priority, t.status, t.person.id, t.person.fullName, t.project.id, t.project.name, true) FROM Task t")
    List<TaskDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.TaskDto(t.id, t.name, t.startTime, t.endTime, t.description, t.priority, t.status, t.person.id, t.person.fullName, t.project.id, t.project.name, true) FROM Task t WHERE t.id = :id")
    Optional<TaskDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    List<Task> findByPersonId(Long personId);
    List<Task> findByProjectId(Long projectId);
}
