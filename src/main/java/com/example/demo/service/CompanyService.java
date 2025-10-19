package com.example.demo.service;

import com.example.demo.company.dto.request.CreateCompanyDto;
import com.example.demo.company.dto.response.CompanyDto;
import com.example.demo.company.dto.request.UpdateCompanyDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> getAllCompanies();
    PagedResponse<CompanyDto> getAllCompanies(Pageable pageable);
    List<CompanyDto> getCompaniesByName(String name);
    CompanyDto getCompanyById(Long id);
    CompanyDto getCompanyByCode(String code);
    CompanyDto createCompany(CreateCompanyDto company);
    CompanyDto updateCompany(UpdateCompanyDto company);
    CompanyDto deleteCompanyById(Long id);
    boolean existsCompanyById(Long id);
}
