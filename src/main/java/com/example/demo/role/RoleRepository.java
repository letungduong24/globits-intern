package com.example.demo.role;

import com.example.demo.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleIgnoreCase(String role);
    boolean existsByRoleAndIdNot(String role, Long id);
}
