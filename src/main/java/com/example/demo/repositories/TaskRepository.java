package com.example.demo.repositories;
import com.example.demo.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByPersonId(Long personId);
    List<Task> findByProjectId(Long projectId);
}
