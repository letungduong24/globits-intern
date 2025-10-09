package com.example.demo.company.service;

import com.example.demo.company.dto.CreateCompanyDto;
import com.example.demo.company.dto.ResponseCompanyDto;
import com.example.demo.company.dto.UpdateCompanyDto;
import com.example.demo.shared.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    List<ResponseCompanyDto> getAllCompanies();
    PagedResponse<ResponseCompanyDto> getAllCompanies(Pageable pageable);
    List<ResponseCompanyDto> getCompaniesByName(String name);
    ResponseCompanyDto getCompanyById(Long id);
    ResponseCompanyDto getCompanyByCode(String code);
    ResponseCompanyDto createCompany(CreateCompanyDto company);
    ResponseCompanyDto updateCompany(UpdateCompanyDto company);
    ResponseCompanyDto deleteCompanyById(Long id);
    boolean existsCompanyById(Long id);
}
