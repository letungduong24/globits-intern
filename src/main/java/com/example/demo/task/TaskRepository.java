package com.example.demo.task;
import com.example.demo.task.dto.response.TaskDto;
import com.example.demo.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {
}
