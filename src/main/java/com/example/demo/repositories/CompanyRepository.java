package com.example.demo.repositories;

import com.example.demo.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByName(String name);
    Optional<Company> findByCode(String code);
    boolean existsByCode(String code);
}
