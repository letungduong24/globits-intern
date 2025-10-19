package com.example.demo.service;

import com.example.demo.dto.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountries();
    CountryDto getCountryById(Long id);
    List<CountryDto> getCountriesByName(String name);
    CountryDto getCountryByCode(String code);
    CountryDto createCountry(CountryDto country);
    CountryDto updateCountry(CountryDto country);
    CountryDto deleteCountryById(Long id);
    boolean existsCountryById(Long id);
}
