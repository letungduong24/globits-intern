package com.example.demo.repositories;

import com.example.demo.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByCompanyId(Long id);
    Department findByCode(String code);
    List<Department> findByParentId(Long id);
    boolean existsByCode(String code);
}
