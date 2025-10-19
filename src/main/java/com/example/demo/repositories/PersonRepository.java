package com.example.demo.repositories;

import com.example.demo.domain.Person;
import com.example.demo.dto.PersonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    // HQL queries để map trực tiếp từ Entity sang DTO
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p WHERE p.fullName LIKE %:fullName%")
    List<PersonDto> findByFullNameContainingIgnoreCaseAsDto(@Param("fullName") String fullName);
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p WHERE p.fullName = :fullName")
    Optional<PersonDto> findByFullNameAsDto(@Param("fullName") String fullName);
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p WHERE p.company.id = :companyId")
    List<PersonDto> findByCompanyIdAsDto(@Param("companyId") Long companyId);
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p JOIN p.projects pr WHERE pr.id = :projectId")
    List<PersonDto> findByProjectsIdAsDto(@Param("projectId") Long projectId);
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p WHERE p.phoneNumber = :phoneNumber")
    Optional<PersonDto> findByPhoneNumberAsDto(@Param("phoneNumber") String phoneNumber);
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p WHERE p.user.id = :userId")
    Optional<PersonDto> findByUserIdAsDto(@Param("userId") Long userId);
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p")
    List<PersonDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.PersonDto(p.id, p.fullName, p.gender, p.birthDate, p.phoneNumber, p.address, p.avatar, p.user.id, p.company.id, p.company.name, true) FROM Person p WHERE p.id = :id")
    Optional<PersonDto> findByIdAsDto(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    List<Person> findByFullNameContainingIgnoreCase(String fullName);
    Optional<Person> findByFullName(String fullName);
    List<Person> findByCompanyId(Long id);
    List<Person> findByProjectsId(Long projectId);
    Optional<Person> findByPhoneNumber(String phoneNumber);
    Optional<Person> findByUserId(Long userId);
    boolean existsByPhoneNumber(String phoneNumber);
}
