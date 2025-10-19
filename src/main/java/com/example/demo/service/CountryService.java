package com.example.demo.service;

import com.example.demo.country.dto.request.CreateCountryDto;
import com.example.demo.country.dto.response.CountryDto;
import com.example.demo.country.dto.request.UpdateCountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountries();
    CountryDto getCountryById(Long id);
    List<CountryDto> getCountriesByName(String name);
    CountryDto getCountryByCode(String code);
    CountryDto createCountry(CreateCountryDto country);
    CountryDto updateCountry(UpdateCountryDto country);
    CountryDto deleteCountryById(Long id);
    boolean existsCountryById(Long id);
}
