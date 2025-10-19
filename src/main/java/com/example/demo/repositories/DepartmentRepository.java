package com.example.demo.repositories;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.DepartmentDto(d.id, d.name, d.code, d.parent.id, d.parent.name, d.company.id, d.company.name, true) FROM Department d LEFT JOIN d.parent LEFT JOIN d.company WHERE d.company.id = :companyId")
    List<DepartmentDto> findByCompanyIdAsDto(@Param("companyId") Long companyId);
    
    @Query("SELECT new com.example.demo.dto.DepartmentDto(d.id, d.name, d.code, d.parent.id, d.parent.name, d.company.id, d.company.name, true) FROM Department d LEFT JOIN d.parent LEFT JOIN d.company WHERE d.code = :code")
    Optional<DepartmentDto> findByCodeAsDto(@Param("code") String code);
    
    @Query("SELECT new com.example.demo.dto.DepartmentDto(d.id, d.name, d.code, d.parent.id, d.parent.name, d.company.id, d.company.name, true) FROM Department d LEFT JOIN d.parent LEFT JOIN d.company WHERE d.parent.id = :parentId")
    List<DepartmentDto> findByParentIdAsDto(@Param("parentId") Long parentId);
    
    @Query("SELECT new com.example.demo.dto.DepartmentDto(d.id, d.name, d.code, d.parent.id, d.parent.name, d.company.id, d.company.name, true) FROM Department d LEFT JOIN d.parent LEFT JOIN d.company")
    List<DepartmentDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.DepartmentDto(d.id, d.name, d.code, d.parent.id, d.parent.name, d.company.id, d.company.name, true) FROM Department d LEFT JOIN d.parent LEFT JOIN d.company WHERE d.id = :id")
    Optional<DepartmentDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    List<Department> findByCompanyId(Long id);
    Department findByCode(String code);
    List<Department> findByParentId(Long id);
    boolean existsByCode(String code);
}
