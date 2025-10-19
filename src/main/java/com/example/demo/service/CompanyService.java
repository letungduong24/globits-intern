package com.example.demo.service;

import com.example.demo.dto.CompanyDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> getAllCompanies();
    PagedResponse<CompanyDto> getAllCompanies(Pageable pageable);
    List<CompanyDto> getCompaniesByName(String name);
    CompanyDto getCompanyById(Long id);
    CompanyDto getCompanyByCode(String code);
    CompanyDto createCompany(CompanyDto company);
    CompanyDto updateCompany(CompanyDto company);
    CompanyDto deleteCompanyById(Long id);
    boolean existsCompanyById(Long id);
}
