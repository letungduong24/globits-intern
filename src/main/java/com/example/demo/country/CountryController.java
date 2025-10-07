package com.example.demo.country;

import com.example.demo.country.dto.CreateCountryDto;
import com.example.demo.country.dto.ResponseCountryDto;
import com.example.demo.country.dto.UpdateCountryDto;
import com.example.demo.country.entity.Country;
import com.example.demo.country.service.CountryService;
import com.example.demo.shared.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public List<ResponseCountryDto> getCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/{name}/by-name")
    public List<ResponseCountryDto> getCountriesByName(@PathVariable String name){
        return countryService.getCountriesByName(name);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResponseCountryDto>>  createCountry(@Valid @RequestBody CreateCountryDto country){
        ResponseCountryDto created = countryService.createCountry(country);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseCountryDto>builder()
                        .success(true)
                        .message("Tạo country thành công")
                        .data(created)
                        .build());

    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponseCountryDto>>  updateCountry(@RequestBody UpdateCountryDto countryDto){
        ResponseCountryDto updated = countryService.updateCountry(countryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseCountryDto>builder()
                        .success(true)
                        .message("Update country thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseCountryDto>> deleteCountryById(@PathVariable Long id) {
        ResponseCountryDto deleted = countryService.deleteCountryById(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseCountryDto>builder()
                        .success(true)
                        .message("Xoá country thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseCountryDto getCountryById(@PathVariable Long id){
        return countryService.getCountryById(id);
    }

    @GetMapping("/{code}/by-code")
    public ResponseCountryDto getCountryByCode(@PathVariable String code){
        return countryService.getCountryByCode(code);
    }



}
