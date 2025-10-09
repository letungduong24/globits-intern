package com.example.demo.company.dto;

import com.example.demo.company.dto.request.CreateCompanyDto;
import com.example.demo.company.dto.request.UpdateCompanyDto;
import com.example.demo.company.dto.response.CompanyDto;
import com.example.demo.company.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public CompanyDto toDTO(Company company) {
        if (company == null) {
            return null;
        }
        
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .code(company.getCode())
                .build();
    }

    public List<CompanyDto> toDTOs(List<Company> companies) {
        if (companies == null) {
            return null;
        }
        
        return companies.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Company toEntity(CreateCompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }
        
        return Company.builder()
                .name(companyDto.getName())
                .address(companyDto.getAddress())
                .code(companyDto.getCode())
                .build();
    }

    public Company updateEntity(Company existingCompany, UpdateCompanyDto updateDto) {
        if (existingCompany == null || updateDto == null) {
            return existingCompany;
        }
        
        if (updateDto.getName() != null) {
            existingCompany.setName(updateDto.getName());
        }
        if (updateDto.getAddress() != null) {
            existingCompany.setAddress(updateDto.getAddress());
        }
        if (updateDto.getCode() != null) {
            existingCompany.setCode(updateDto.getCode());
        }
        
        return existingCompany;
    }
}
