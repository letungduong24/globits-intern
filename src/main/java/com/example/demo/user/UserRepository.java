package com.example.demo.user;

import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = "person")
    Optional<User> findWithPersonById(Long id);
    @EntityGraph(attributePaths = "person")
    Optional<User> findWithPersonByEmail(String email);
    boolean existsByEmail(String email);
}
