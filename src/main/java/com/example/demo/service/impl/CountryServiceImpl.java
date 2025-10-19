package com.example.demo.service.impl;

import com.example.demo.repositories.CountryRepository;
import com.example.demo.country.dto.CountryMapper;
import com.example.demo.country.dto.request.CreateCountryDto;
import com.example.demo.country.dto.response.CountryDto;
import com.example.demo.country.dto.request.UpdateCountryDto;
import com.example.demo.domain.Country;
import com.example.demo.service.CountryService;
import com.example.demo.shared.exception.DuplicateResourceException;
import com.example.demo.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {

        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<CountryDto> getAllCountries() {

        return countryMapper.toDTOs(countryRepository.findAll());
    }

    @Override
    public List<CountryDto> getCountriesByName(String name) {
        return countryMapper.toDTOs(countryRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
        return countryMapper.toDTO(country);
    }

    @Override
    public CountryDto getCountryByCode(String code) {
        Country country = countryRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
        return countryMapper.toDTO(country);
    }

    @Override
    public CountryDto createCountry(CreateCountryDto countryDto) {
        countryRepository.findByCode(countryDto.getCode())
                .ifPresent(c -> { throw new DuplicateResourceException("Country đã tồn tại"); });
        Country country = countryMapper.toEntity(countryDto);
        Country saved = countryRepository.save(country);
        return countryMapper.toDTO(saved);
    }

    @Override
    public CountryDto updateCountry(UpdateCountryDto countryDto) {
        Country country = countryRepository.findById(countryDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));

        if (
                countryDto.getCode() != null &&
                !countryDto.getCode().equals(country.getCode()) &&
                countryRepository.existsByCodeAndIdNot(countryDto.getCode(), countryDto.getId())
        ) {
            throw new DuplicateResourceException("Code đã tồn tại");
        }

        countryMapper.updateEntity(country, countryDto);
        countryRepository.save(country);
        return countryMapper.toDTO(country);
    }

    @Override
    public CountryDto deleteCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));

        CountryDto dto = CountryDto.builder()
                .id(country.getId())
                .code(country.getCode())
                .name(country.getName())
                .build();

        countryRepository.delete(country);

        return dto;
    }

    @Override
    public boolean existsCountryById(Long id) {
        return countryRepository.existsById(id);
    }
}
