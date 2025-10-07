package com.example.demo.company;

import com.example.demo.company.dto.CreateCompanyDto;
import com.example.demo.company.dto.ResponseCompanyDto;
import com.example.demo.company.dto.UpdateCompanyDto;
import com.example.demo.company.service.CompanyService;
import com.example.demo.shared.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public List<ResponseCompanyDto> getCompanies(){
        return companyService.getAllCompanies();
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> createCompany(@Valid @RequestBody CreateCompanyDto company){
        ResponseCompanyDto created = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Tạo công ty thành công")
                        .data(created)
                        .build());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> updateCompany(@Valid @RequestBody UpdateCompanyDto companyDto){
        ResponseCompanyDto updated = companyService.updateCompany(companyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Cập nhật công ty thành công")
                        .data(updated)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseCompanyDto>> deleteCompanyById(@PathVariable Long id) {
        ResponseCompanyDto deleted = companyService.deleteCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponseCompanyDto>builder()
                        .success(true)
                        .message("Xóa công ty thành công")
                        .data(deleted)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseCompanyDto getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id);
    }

    @GetMapping("/{name}/by-name")
    public List<ResponseCompanyDto> getCompaniesByName(@PathVariable String name){
        return companyService.getCompaniesByName(name);
    }

    @GetMapping("/{code}/by-code")
    public ResponseCompanyDto getCompanyByCode(@PathVariable String code){
        return companyService.getCompanyByCode(code);
    }
}
