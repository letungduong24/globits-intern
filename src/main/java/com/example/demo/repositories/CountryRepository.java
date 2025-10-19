package com.example.demo.repositories;

import com.example.demo.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
    List<Country> findByNameContainingIgnoreCase(String name);
    boolean existsByCodeAndIdNot(String code, Long id);
}
