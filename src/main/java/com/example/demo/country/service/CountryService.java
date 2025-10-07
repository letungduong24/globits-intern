package com.example.demo.country.service;

import com.example.demo.country.dto.CreateCountryDto;
import com.example.demo.country.dto.ResponseCountryDto;
import com.example.demo.country.dto.UpdateCountryDto;
import com.example.demo.country.entity.Country;

import java.util.List;

public interface CountryService {
    List<ResponseCountryDto> getAllCountries();
    ResponseCountryDto getCountryById(Long id);
    List<ResponseCountryDto> getCountriesByName(String name);
    ResponseCountryDto getCountryByCode(String code);
    ResponseCountryDto createCountry(CreateCountryDto country);
    ResponseCountryDto updateCountry(UpdateCountryDto country);
    ResponseCountryDto deleteCountryById(Long id);
    boolean existsCountryById(Long id);
}
