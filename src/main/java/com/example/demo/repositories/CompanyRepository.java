package com.example.demo.repositories;

import com.example.demo.dto.CompanyDto;
import com.example.demo.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.CompanyDto(c.id, c.name, c.address, c.code, true) FROM Company c WHERE c.name = :name")
    List<CompanyDto> findByNameAsDto(@Param("name") String name);
    
    @Query("SELECT new com.example.demo.dto.CompanyDto(c.id, c.name, c.address, c.code, true) FROM Company c WHERE c.code = :code")
    Optional<CompanyDto> findByCodeAsDto(@Param("code") String code);
    
    @Query("SELECT new com.example.demo.dto.CompanyDto(c.id, c.name, c.address, c.code, true) FROM Company c")
    List<CompanyDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.CompanyDto(c.id, c.name, c.address, c.code, true) FROM Company c WHERE c.id = :id")
    Optional<CompanyDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    List<Company> findByName(String name);
    Optional<Company> findByCode(String code);
    boolean existsByCode(String code);
}
