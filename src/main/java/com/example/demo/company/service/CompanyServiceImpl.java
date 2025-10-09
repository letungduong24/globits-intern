package com.example.demo.company.service;

import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import com.example.demo.company.dto.request.CreateCompanyDto;
import com.example.demo.company.dto.response.CompanyDto;
import com.example.demo.company.dto.request.UpdateCompanyDto;
import com.example.demo.company.entity.Company;
import com.example.demo.company.CompanyRepository;
import com.example.demo.company.dto.CompanyMapper;
import com.example.demo.shared.response.PagedResponse;
import com.example.demo.shared.util.PagedUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyMapper.toDTOs(companyRepository.findAll());
    }

    @Override
    public PagedResponse<CompanyDto> getAllCompanies(Pageable pageable){
        Page<Company> page = companyRepository.findAll(pageable);
        List<CompanyDto> contentMapped = companyMapper.toDTOs(page.getContent());

        return PagedUtil.ToPagedResponse(page, contentMapped);
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        return companyMapper.toDTO(company);
    }

    @Override
    public CompanyDto getCompanyByCode(String code) {
        Company company = companyRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        return companyMapper.toDTO(company);
    }

    @Override
    public List<CompanyDto> getCompaniesByName(String name) {
        return companyMapper.toDTOs(companyRepository.findByName(name));
    }

    @Override
    public CompanyDto createCompany(CreateCompanyDto companyDto) {
        if (companyRepository.existsByCode(companyDto.getCode())) {
            throw new DuplicateResourceException("Mã công ty đã tồn tại");
        }
        
        Company company = companyMapper.toEntity(companyDto);
        Company saved = companyRepository.save(company);
        return companyMapper.toDTO(saved);
    }

    @Override
    public CompanyDto updateCompany(UpdateCompanyDto companyDto) {
        Company company = companyRepository.findById(companyDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));
        
        if (companyDto.getCode() != null && !companyDto.getCode().equals(company.getCode())) {
            if (companyRepository.existsByCode(companyDto.getCode())) {
                throw new DuplicateResourceException("Mã công ty đã tồn tại");
            }
        }
        
        companyMapper.updateEntity(company, companyDto);
        Company saved = companyRepository.save(company);
        return companyMapper.toDTO(saved);
    }

    @Override
    public CompanyDto deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công ty"));

        CompanyDto dto = companyMapper.toDTO(company);
        companyRepository.delete(company);
        return dto;
    }

    @Override
    public boolean existsCompanyById(Long id) {
        return companyRepository.existsById(id);
    }
}
