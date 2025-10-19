package com.example.demo.service.impl;

import com.example.demo.repositories.CountryRepository;
import com.example.demo.dto.CountryDto;
import com.example.demo.domain.Country;
import com.example.demo.service.CountryService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAllAsDto();
    }

    @Override
    public List<CountryDto> getCountriesByName(String name) {
        return countryRepository.findByNameContainingIgnoreCaseAsDto(name);
    }

    @Override
    public CountryDto getCountryById(Long id) {
        return countryRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
    }

    @Override
    public CountryDto getCountryByCode(String code) {
        return countryRepository.findByCodeAsDto(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
    }

    @Override
    public CountryDto createCountry(CountryDto countryDto) {
        countryRepository.findByCode(countryDto.getCode())
                .ifPresent(c -> { throw new DuplicateResourceException("Country đã tồn tại"); });
        Country country = countryDto.toEntity();
        Country saved = countryRepository.save(country);
        return CountryDto.fromEntity(saved);
    }

    @Override
    public CountryDto updateCountry(CountryDto countryDto) {
        Country country = countryRepository.findById(countryDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));

        if (
                countryDto.getCode() != null &&
                !countryDto.getCode().equals(country.getCode()) &&
                countryRepository.existsByCodeAndIdNot(countryDto.getCode(), countryDto.getId())
        ) {
            throw new DuplicateResourceException("Code đã tồn tại");
        }

        // Cập nhật các trường
        if (countryDto.getName() != null) {
            country.setName(countryDto.getName());
        }
        if (countryDto.getCode() != null) {
            country.setCode(countryDto.getCode());
        }
        if (countryDto.getDescription() != null) {
            country.setDescription(countryDto.getDescription());
        }
        
        Country saved = countryRepository.save(country);
        return CountryDto.fromEntity(saved);
    }

    @Override
    public CountryDto deleteCountryById(Long id) {
        CountryDto dto = countryRepository.findByIdAsDto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
        
        countryRepository.deleteById(id);
        return dto;
    }

    @Override
    public boolean existsCountryById(Long id) {
        return countryRepository.existsById(id);
    }
}
