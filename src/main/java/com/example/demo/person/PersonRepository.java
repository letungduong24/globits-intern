package com.example.demo.person;

import com.example.demo.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFullNameContainingIgnoreCase(String fullName);
    Optional<Person> findByFullName(String fullName);
    List<Person> findByCompanyId(Long id);
    List<Person> findByProjectsId(Long projectId);
    Optional<Person> findByPhoneNumber(String phoneNumber);
    Optional<Person> findByUserId(Long userId);
    boolean existsByPhoneNumber(String phoneNumber);
}
