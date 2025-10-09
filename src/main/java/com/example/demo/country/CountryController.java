package com.example.demo.country;

import com.example.demo.country.dto.request.CreateCountryDto;
import com.example.demo.country.dto.response.CountryDto;
import com.example.demo.country.dto.request.UpdateCountryDto;
import com.example.demo.country.service.CountryService;
import com.example.demo.shared.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<CountryDto>>> getCountries(){
        List<CountryDto> countries = countryService.getAllCountries();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CountryDto>>builder()
                        .success(true)
                        .message("Lấy countries thành công")
                        .data(countries)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<CountryDto>>> getCountriesByName(@PathVariable String name){
        List<CountryDto> countries = countryService.getCountriesByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CountryDto>>builder()
                        .success(true)
                        .message("Lấy countries thành công")
                        .data(countries)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<CountryDto>>  createCountry(@Valid @RequestBody CreateCountryDto country){
        CountryDto created = countryService.createCountry(country);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CountryDto>builder()
                        .success(true)
                        .message("Tạo country thành công")
                        .data(created)
                        .build());

    }

    @PutMapping()
    public ResponseEntity<ApiResponse<CountryDto>>  updateCountry(@RequestBody UpdateCountryDto countryDto){
        CountryDto updated = countryService.updateCountry(countryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CountryDto>builder()
                        .success(true)
                        .message("Update country thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryDto>> deleteCountryById(@PathVariable Long id) {
        CountryDto deleted = countryService.deleteCountryById(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CountryDto>builder()
                        .success(true)
                        .message("Xoá country thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryDto>> getCountryById(@PathVariable Long id){
        CountryDto country = countryService.getCountryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CountryDto>builder()
                        .success(true)
                        .message("Lấy country thành công")
                        .data(country)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<CountryDto>> getCountryByCode(@PathVariable String code){
        CountryDto country = countryService.getCountryByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CountryDto>builder()
                        .success(true)
                        .message("Lấy country thành công")
                        .data(country)
                        .build());
    }



}
