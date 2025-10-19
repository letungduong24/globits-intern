package com.example.demo.repositories;

import com.example.demo.domain.Project;
import com.example.demo.dto.ProjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.ProjectDto(p.id, p.name, p.code, p.description, p.company.id, p.company.name, true) FROM Project p WHERE p.name = :name")
    List<ProjectDto> findByNameAsDto(@Param("name") String name);
    
    @Query("SELECT new com.example.demo.dto.ProjectDto(p.id, p.name, p.code, p.description, p.company.id, p.company.name, true) FROM Project p WHERE p.code = :code")
    Optional<ProjectDto> findByCodeAsDto(@Param("code") String code);
    
    @Query("SELECT new com.example.demo.dto.ProjectDto(p.id, p.name, p.code, p.description, p.company.id, p.company.name, true) FROM Project p WHERE p.company.id = :companyId")
    List<ProjectDto> findByCompanyIdAsDto(@Param("companyId") Long companyId);
    
    @Query("SELECT new com.example.demo.dto.ProjectDto(p.id, p.name, p.code, p.description, p.company.id, p.company.name, true) FROM Project p")
    List<ProjectDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.ProjectDto(p.id, p.name, p.code, p.description, p.company.id, p.company.name, true) FROM Project p WHERE p.id = :id")
    Optional<ProjectDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    List<Project> findByName(String name);
    Optional<Project> findByCode(String code);
    boolean existsByCode(String code);
    List<Project> findByCompanyId(Long companyId);
}
