package com.example.demo.repositories;

import com.example.demo.domain.Role;
import com.example.demo.dto.RoleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.RoleDto(r.id, r.role, r.description, true) FROM Role r WHERE LOWER(r.role) = LOWER(:role)")
    Optional<RoleDto> findByRoleIgnoreCaseAsDto(@Param("role") String role);
    
    @Query("SELECT new com.example.demo.dto.RoleDto(r.id, r.role, r.description, true) FROM Role r")
    List<RoleDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.RoleDto(r.id, r.role, r.description, true) FROM Role r WHERE r.id = :id")
    Optional<RoleDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    Optional<Role> findByRoleIgnoreCase(String role);
    boolean existsByRoleAndIdNot(String role, Long id);
}
