package com.example.demo.country.service;

import com.example.demo.country.CountryRepository;
import com.example.demo.country.dto.CountryMapper;
import com.example.demo.country.dto.CreateCountryDto;
import com.example.demo.country.dto.ResponseCountryDto;
import com.example.demo.country.dto.UpdateCountryDto;
import com.example.demo.country.entity.Country;
import com.example.demo.shared.Exception.DuplicateResourceException;
import com.example.demo.shared.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {

        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<ResponseCountryDto> getAllCountries() {

        return countryMapper.toDTOs(countryRepository.findAll());
    }

    @Override
    public List<ResponseCountryDto> getCountriesByName(String name) {
        return countryMapper.toDTOs(countryRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public ResponseCountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
        return countryMapper.toDTO(country);
    }

    @Override
    public ResponseCountryDto getCountryByCode(String code) {
        Country country = countryRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));
        return countryMapper.toDTO(country);
    }

    @Override
    public ResponseCountryDto createCountry(CreateCountryDto countryDto) {
        countryRepository.findByCode(countryDto.getCode())
                .ifPresent(c -> { throw new DuplicateResourceException("Country đã tồn tại"); });
        Country country = countryMapper.toEntity(countryDto);
        Country saved = countryRepository.save(country);
        return countryMapper.toDTO(saved);
    }

    @Override
    public ResponseCountryDto updateCountry(UpdateCountryDto countryDto) {
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
    public ResponseCountryDto deleteCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy country"));

        ResponseCountryDto dto = ResponseCountryDto.builder()
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
