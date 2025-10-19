package com.example.demo.repositories;

import com.example.demo.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);
    Optional<Project> findByCode(String code);
    boolean existsByCode(String code);
    List<Project> findByCompanyId(Long companyId);
}
