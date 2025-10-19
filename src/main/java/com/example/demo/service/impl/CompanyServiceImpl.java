package com.example.demo.service.impl;

import com.example.demo.service.CompanyService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.dto.CompanyDto;
import com.example.demo.domain.Company;
import com.example.demo.repositories.CompanyRepository;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAllAsDto();
    }

    @Override
    public PagedResponse<CompanyDto> getAllCompanies(Pageable pageable){
        Page<Company> page = companyRepository.findAll(pageable);
        List<CompanyDto> contentMapped = companyRepository.findAllAsDto();

        return PagedUtil.ToPagedResponse(page, contentMapped);
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        return companyRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
    }

    @Override
    public CompanyDto getCompanyByCode(String code) {
        return companyRepository.findByCodeAsDto(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
    }

    @Override
    public List<CompanyDto> getCompaniesByName(String name) {
        return companyRepository.findByNameAsDto(name);
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        if (companyRepository.existsByCode(companyDto.getCode())) {
            throw new DuplicateResourceException("Mã công ty đã tồn tại");
        }
        
        Company company = companyDto.toEntity();
        Company saved = companyRepository.save(company);
        return CompanyDto.fromEntity(saved);
    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto) {
        Company existingCompany = companyRepository.findById(companyDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        
        if (companyDto.getCode() != null && !companyDto.getCode().equals(existingCompany.getCode())) {
            if (companyRepository.existsByCode(companyDto.getCode())) {
                throw new DuplicateResourceException("Mã công ty đã tồn tại");
            }
        }
        
        // Cập nhật các trường
        if (companyDto.getName() != null) {
            existingCompany.setName(companyDto.getName());
        }
        if (companyDto.getAddress() != null) {
            existingCompany.setAddress(companyDto.getAddress());
        }
        if (companyDto.getCode() != null) {
            existingCompany.setCode(companyDto.getCode());
        }
        
        Company saved = companyRepository.save(existingCompany);
        return CompanyDto.fromEntity(saved);
    }

    @Override
    public CompanyDto deleteCompanyById(Long id) {
        CompanyDto dto = companyRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        
        companyRepository.deleteById(id);
        return dto;
    }

    @Override
    public boolean existsCompanyById(Long id) {
        return companyRepository.existsById(id);
    }
}
