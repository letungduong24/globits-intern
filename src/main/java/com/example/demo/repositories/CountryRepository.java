package com.example.demo.repositories;

import com.example.demo.dto.CountryDto;
import com.example.demo.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.CountryDto(c.id, c.name, c.description, c.code, true) FROM Country c WHERE c.code = :code")
    Optional<CountryDto> findByCodeAsDto(@Param("code") String code);
    
    @Query("SELECT new com.example.demo.dto.CountryDto(c.id, c.name, c.description, c.code, true) FROM Country c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CountryDto> findByNameContainingIgnoreCaseAsDto(@Param("name") String name);
    
    @Query("SELECT new com.example.demo.dto.CountryDto(c.id, c.name, c.description, c.code, true) FROM Country c")
    List<CountryDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.CountryDto(c.id, c.name, c.description, c.code, true) FROM Country c WHERE c.id = :id")
    Optional<CountryDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    Optional<Country> findByCode(String code);
    List<Country> findByNameContainingIgnoreCase(String name);
    boolean existsByCodeAndIdNot(String code, Long id);
}
