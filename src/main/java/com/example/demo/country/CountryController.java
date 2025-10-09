package com.example.demo.country;

import com.example.demo.country.dto.CreateCountryDto;
import com.example.demo.country.dto.ResponseCountryDto;
import com.example.demo.country.dto.UpdateCountryDto;
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
    public ResponseEntity<ApiResponse<List<ResponseCountryDto>>> getCountries(){
        List<ResponseCountryDto> countries = countryService.getAllCountries();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseCountryDto>>builder()
                        .success(true)
                        .message("Lấy countries thành công")
                        .data(countries)
                        .build());
    }

    @GetMapping("/{name}/by-name")
    public ResponseEntity<ApiResponse<List<ResponseCountryDto>>> getCountriesByName(@PathVariable String name){
        List<ResponseCountryDto> countries = countryService.getCountriesByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ResponseCountryDto>>builder()
                        .success(true)
                        .message("Lấy countries thành công")
                        .data(countries)
                        .build());
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
    public ResponseEntity<ApiResponse<ResponseCountryDto>> getCountryById(@PathVariable Long id){
        ResponseCountryDto country = countryService.getCountryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCountryDto>builder()
                        .success(true)
                        .message("Lấy country thành công")
                        .data(country)
                        .build());
    }

    @GetMapping("/{code}/by-code")
    public ResponseEntity<ApiResponse<ResponseCountryDto>> getCountryByCode(@PathVariable String code){
        ResponseCountryDto country = countryService.getCountryByCode(code);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCountryDto>builder()
                        .success(true)
                        .message("Lấy country thành công")
                        .data(country)
                        .build());
    }



}
