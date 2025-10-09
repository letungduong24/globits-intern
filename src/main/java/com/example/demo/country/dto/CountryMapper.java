package com.example.demo.country.dto;

import com.example.demo.country.dto.request.CreateCountryDto;
import com.example.demo.country.dto.request.UpdateCountryDto;
import com.example.demo.country.dto.response.CountryDto;
import com.example.demo.country.entity.Country;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryMapper {

    public CountryDto toDTO(Country country) {
        if (country == null) {
            return null;
        }
        
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .description(country.getDescription())
                .code(country.getCode())
                .build();
    }

    public List<CountryDto> toDTOs(List<Country> countries) {
        if (countries == null) {
            return null;
        }
        
        return countries.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Country toEntity(CreateCountryDto countryDto) {
        if (countryDto == null) {
            return null;
        }
        
        return Country.builder()
                .name(countryDto.getName())
                .description(countryDto.getDescription())
                .code(countryDto.getCode())
                .build();
    }

    public Country updateEntity(Country existingCountry, UpdateCountryDto updateDto) {
        if (updateDto.getName() != null) {
            existingCountry.setName(updateDto.getName());
        }
        if (updateDto.getDescription() != null) {
            existingCountry.setDescription(updateDto.getDescription());
        }
        if (updateDto.getCode() != null) {
            existingCountry.setCode(updateDto.getCode());
        }
        
        return existingCountry;
    }
}
