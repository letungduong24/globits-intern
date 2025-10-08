package com.example.demo.country;

import com.example.demo.country.entity.Country;
import com.example.demo.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
    List<Country> findByNameContainingIgnoreCase(String name);
    boolean existsByCodeAndIdNot(String code, Long id);
}
