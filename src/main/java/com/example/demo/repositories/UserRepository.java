package com.example.demo.repositories;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // HQL queries để lấy User với roles
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdWithRoles(@Param("id") Long id);
    
    // Giữ lại các method cũ cho compatibility
    @Query("SELECT new com.example.demo.dto.UserDto(u.id, u.email, u.password, u.isActive, true) FROM User u WHERE u.email = :email")
    Optional<UserDto> findByEmailAsDto(@Param("email") String email);
    
    @Query("SELECT new com.example.demo.dto.UserDto(u.id, u.email, u.password, u.isActive, true) FROM User u")
    List<UserDto> findAllAsDto();
    
    @Query("SELECT new com.example.demo.dto.UserDto(u.id, u.email, u.password, u.isActive, true) FROM User u WHERE u.id = :id")
    Optional<UserDto> findByIdAsDto(@Param("id") Long id);
    
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
